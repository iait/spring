# Aspect-Oriented Programming

Sometimes it’s not relevant to have parts of the code in the same place with the business logic because it makes the app more difficult to understand. 
A solution is to move part of the code aside from the business logic implementation using aspects.

Aspects are a way the framework intercepts method calls and possibly alters the execution of methods. 
You can affect the execution of specific method calls you select. 
This technique helps you extract part of the logic belonging to the executing method. 
In certain scenarios, decoupling a part of the code helps make that method easier to understand.
It allows the developer to focus only on the relevant details discussed when reading the method logic.

Spring uses aspects to implement a lot of the crucial capabilities it offers.
Understanding how the framework works can save you many hours of debugging later when you face a specific problem.
A pertinent example of Spring capability that uses aspects is *transactionality*.

An aspect is simply a piece of logic the framework executes when you call specific methods of your choice. 
When designing an aspect, you define the following:

* What code you want Spring to execute when you call specific methods. This is named an **aspect**.
* When the app should execute this logic of the aspect (e.g., before or after the method call, instead of the method call). This is named the **advice**.
* Which methods the framework needs to intercept and execute the aspect for them. This is named a **pointcut**.

As in the case of the dependency injection, to use aspects you need the framework to manage the objects for which you want to apply aspects.
The bean that declares the method intercepted by an aspect is named the **target object**.

But how does Spring intercept each method call and apply the aspect logic?
Spring won’t directly give you an instance reference for the bean when you request it from the context. 
Instead, Spring gives you an object that calls the aspect logic instead of the actual method.
We say that Spring gives you a **proxy** object instead of the real bean.
This approach is named **weaving**.

## Intercept method execution

Suppose you have an application that implements multiple use cases in its service classes. 
Your app is required to store the time it started and ended for each use case execution. 
You decided to implement a functionality to log all the events where a use case begins and ends.

To create an aspect, you follow these steps:
1. Enable the aspect mechanism in your Spring app by annotating the configuration class with the `@EnableAspectJAutoProxy` annotation.
2. Create a new class, and annotate it with the `@Aspect` annotation. Using either `@Bean` or stereotype annotations, add a bean for this class in the Spring context.
3. Define a method that will implement the aspect logic and tell Spring when and which methods to intercept using an advice annotation (e.g. `@Around`).
4. Implement the aspect logic.

```java
@Component
@Aspect
public class LoggingAspect {

    private Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Around("execution(* org.example.services.*.*(..))")
    public void log(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Method will execute");
        joinPoint.proceed();
        logger.info("Method executed");
    }
}
```

Example in [05-aspects-a](05-aspects-a/).

The expression used as a parameter to the `@Around` annotation tells Spring which method calls to intercept.
This expression language is called AspectJ pointcut language.
[AspectJ documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-ataspectj).

The `ProceedingJoinPoint` parameter represents the intercepted method.
The main thing you do with this parameter is tell the aspect when it should delegate further to the actual method.

The expression in the example tells Spring to intercept any method defined in a class that is in the `org.example.services` package, regardless of the method’s return type, the class it belongs to, the name of the method, or the parameters the method receives.

## Alter parameters and return

Aspects not only can intercept a method and alter its execution, but they can also intercept the parameters used to call the method and possibly alter them or the value the intercepted method returns.

The `ProceedingJoinPoint` parameter of the aspect method represents the intercepted method.
You can use this parameter to get any information related to the intercepted method (parameters, method name, target object, and so on).

```java
String methodName = joinPoint.getSignature().getName();
Object [] arguments = joinPoint.getArgs();
```

Example in [05-aspects-b](05-aspects-b/).

```java
@Around("execution(* org.example.services.CommentService.publishComment(..))")
public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
    
    String methodName = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();
    logger.info("Method " + methodName + " with args " + asList(args) + " will execute");

    Object returned = joinPoint.proceed();

    logger.info("Method executed and returned " + returned);
    return returned;
}
```

But aspects are even more powerful. 
They can alter the execution of the intercepted method by:
* Changing the value of the parameters sent to the method.
* Changing the returned value received by the caller.
* Throwing an exception to the caller or catching and treating an exception thrown by the intercepted method.

```java
@Around("execution(* org.example.services.CommentService.publishComment(..))")
public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

    String methodName = joinPoint.getSignature().getName();
    List<Object> args = asList(joinPoint.getArgs());
    logger.info("Method " + methodName + " with args " + args + " will execute");

    Object returned = joinPoint.proceed(new Object[]{new Comment("Jane", "Bye!")});

    logger.info("Method executed and returned " + returned);
    return "FAILED";
}
```

You should only use them to hide irrelevant lines of code that can easily be implied.
Aspects are so powerful they can lead you to hide relevant code and make your app more difficult to maintain. 
Use aspects with caution!

## Intercept annotated methods

You can use annotations to mark the methods you want an aspect to intercept with a comfortable syntax that allows you also to avoid writing complex AspectJ pointcut expressions.

To achieve this objective, you need to do the following:
1. Define a custom annotation, and make it accessible at runtime. For example, `@ToLog`.
2. Use a different AspectJ pointcut expression for the aspect method to tell the aspect to intercept the methods annotated with the custom annotation. For example, `@Around("@annotation(ToLog)")`.

By default, in Java annotations cannot be intercepted at runtime. 
You need to explicitly specify that someone can intercept annotations by setting the retention policy to runtime with `@Retention(RetentionPolicy.RUNTIME)`.
The `@Target` annotation specifies which language elements we can use this annotation for.
By default, you can annotate any language elements, but it’s always a good idea to restrict the annotation to only what you make it for, for example `@Target(ElementType.METHOD)`.

Example in [05-aspects-c](05-aspects-c/).

## Advice annotations

With the `@Around` advice annotation you can cover any implementation case: you can do things before, after, or even instead of the intercepted method. 
You can alter the logic any way you want from the aspect.
But you don’t necessarily always need all this flexibility.
For simple scenarios, Spring offers four alternative advice annotations that are less powerful than `@Around`.
* `@Before`. Calls the method defining the aspect logic before the execution of the intercepted method.
* `@AfterReturning`. Calls the method defining the aspect logic after the method successfully returns, and provides the returned value as a parameter to the aspect method. The aspect method isn’t called if the intercepted method throws an exception.
* `@AfterThrowing`. Calls the method defining the aspect logic if the intercepted method throws an exception, and provides the exception instance as a parameter to the aspect method.
* `@After`. Calls the method defining the aspect logic only after the intercepted method execution, whether the method successfully returned or threw an exception.

In these cases, the aspect methods don’t receive the `ProceedingJoinPoint` parameter, and they cannot decide when to delegate to the intercepted method.

## Aspect execution chain

In a real-world app, a method is often intercepted by more than one aspect.
For example, we have a method for which we want to log the execution and apply some security constraints.
We often have aspects taking care of such responsibilities, so we have two aspects acting on the same method’s execution in this scenario.

* `SecurityAspect`. Applies the security restrictions. This aspect intercepts the method, validates the call, and in some conditions doesn’t forward the call to the intercepted method.
* `LoggingAspect`. Logs the beginning and end of the intercepted method execution.

By default, Spring doesn’t guarantee the order in which two aspects in the same execution chain are called. 
If the execution order is not relevant, then you just need to define the aspects and leave the framework to execute them in whatever order. 
If you need to define the aspects’ execution order, you can use the `@Order` annotation. 
This annotation receives an ordinal (a number) representing the order in the execution chain for a specific aspect. 
The smaller the number, the earlier that aspect executes. 
If two values are the same, the order of execution is again not defined.

Example: [05-aspects-d](05-aspects-d/)
