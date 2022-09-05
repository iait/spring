# Dependency Injection

Example:

Say we have two instances in the Spring context: a parrot and a person.
We want to make the person own the parrot.
In other words, we need to link the two instances.
We do this so that one object can then delegate to the other in the implementation of their responsibilities.
You can do this using a **wiring** approach, which implies directly calling the methods that declare the beans to establish the link between them, or through **auto-wiring**.

## Wiring calling bean method

Example: [02-dependency-injection-a](02-dependency-injection-a/)

We establish the relationship between the person and the parrot by directly calling the method that returns the bean we want to set.

```java
@Configuration
public class ProjectConfig {

     @Bean
    public Parrot parrot() {
         return new Parrot("Koko");
     }

     @Bean
     public Person person() {
         return new Person("Ella", parrot());
     }
}
```

If the *parrot* bean already exists in the context, then instead of calling the `parrot()` method, Spring will directly take the instance from its context.

When two methods annotated with `@Bean` call each other, Spring knows you want to create a link between the beans.
If the bean already exists in the context, Spring returns the existing bean without forwarding the call to the `@Bean` method.
If the bean doesn’t exist, Spring creates the bean, adds it to the context, and returns its reference.

## Wiring through method parameter

Example: [02-dependency-injection-b](02-dependency-injection-b/)

Instead of directly calling the method that defines the bean we wish to refer to, we add a parameter to the method of the corresponding type of object, and we rely on Spring to provide us a value through that parameter.
With this approach, it doesn't matter if the bean we want to refer to is defined with a method annotated with `@Bean` or using a stereotype annotation like `@Component`.

```java
@Configuration
public class ProjectConfig {

     @Bean
    public Parrot parrot() {
         return new Parrot("Koko");
     }

     @Bean
     public Person person(Parrot parrot) {
         return new Person("Ella", parrot);
     }
}
```

By defining a parameter to the method, we instruct Spring to provide us a bean of the type of that parameter from its context.

## Using auto-wiring

Using the `@Autowired` annotation, we mark an object’s property where we want Spring to inject a value from the context, and we mark this intention directly in the class that defines the object that needs the dependency.
There are three ways we can use the @Autowired annotation:

**1-** Injecting the value in the field of the class, which you usually find in examples and proofs of concept.

Example: [02-dependency-injection-c](02-dependency-injection-c/)

```java
@Component
public class Person {

    private String name;

    @Autowired
    private Parrot parrot;

    // ...
}
```

This approach is not desired in production code because you want to make your app maintainable and testable. 
By injecting the value directly in the field:

* You don’t have the option to make the field final, and this way, make sure no one can change its value after initialization.
* It’s more difficult to manage the value yourself at initialization; you sometimes need to create instances of the objects and easily manage the unit tests’ dependencies.

**2-** Injecting the value through the constructor parameters of the class approach that you’ll use most often in real-world scenarios.

Example: [02-dependency-injection-d](02-dependency-injection-d/)

```java
@Component
public class Person {

    private String name;

    private final Parrot parrot;

    public Person(Parrot parrot) {
        this.parrot = parrot;
    }

    // ...
}
```

This approach is the one used most often in production code. 
It enables you to define the fields as final, ensuring no one can change their value after Spring initializes them.
The possibility to set the values when calling the constructor also helps you when writing specific unit tests where you don’t want to rely on Spring making the field injection for you.

Starting with Spring version 4.3, when you only have one constructor in the class, you can omit writing the `@Autowired` annotation.

**3-** Injecting the value through the setter, which you’ll rarely use in production-ready code.

Example: [02-dependency-injection-e](02-dependency-injection-e/)

```java
@Component
public class Person {

    private String name;
    private Parrot parrot;

    @Autowired
    public setParrot(Parrot parrot) {
        this.parrot = parrot;
    }

    // ...
}
```

This approach is not recommended as it’s more challenging to read, it doesn’t allow you to make the field final, and it doesn’t help you in making the testing easier.

## Dealing with circular dependencies

Example: [02-dependency-injection-f](02-dependency-injection-f/)

A scenario often encountered in practice is generating a circular dependency by mistake.
A circular dependency is a situation in which, to create a bean (let’s name it Bean A), Spring needs to inject another bean that doesn’t exist yet (Bean B).
But Bean B also requests a dependency to Bean A. 
So, to create Bean B, Spring needs first to have Bean A. 
Spring is now in a deadlock.

*It cannot create Bean A because it needs Bean B, and it cannot create Bean B because it needs Bean A.*

In this situation, an exception `BeanCurrentlyInCreationException` will be thrown by Spring.
With this exception, Spring tries to tell you the problem it encountered.
The exception message is quite clear: Spring deals with a circular dependency and the classes that caused the situation.
Whenever you find such an exception, you need to go to the classes specified by the exception and eliminate the circular dependency.

## Choosing from multiple beans

Suppose the scenario in which Spring needs to inject a value into a parameter or class field but has multiple beans of the same type to choose from.
Depending on your implementation, you have the following cases:

**1-** The identifier of the parameter matches the name of one of the beans from the context (which, remember, is the same as the name of the method annotated with `@Bean` that returns its value). 
In this case, Spring will choose the bean for which the name is the same as the parameter.

Example: [02-dependency-injection-g](02-dependency-injection-g/)

```java
@Configuration
public class ProjectConfig {

    @Bean
    public Parrot parrot1() {
        return new Parrot("Koko");
    }

    @Bean
    public Parrot parrot2() {
        return new Parrot("Miki");
    }

    @Bean
    public Person person(Parrot parrot2) {
        return new Person("Ella", parrot2);
    }
}
```

**2-** The identifier of the parameter doesn’t match any of the bean names from the context. Then you have the following options:

*a)* You marked one of the beans as primary (using the `@Primary` annotation). In this case, Spring will select the primary bean for injection.

Example: [02-dependency-injection-h](02-dependency-injection-h/)
```java
@Configuration
public class ProjectConfig {

    @Bean
    @Primary
    public Parrot parrot1() {
        return new Parrot("Koko");
    }

    @Bean
    public Parrot parrot2() {
        return new Parrot("Miki");
    }

    @Bean
    public Person person(Parrot parrot) {
        return new Person("Ella", parrot);
    }
}
```

In a real-world scenario, it's preferred to avoid relying on the name of the parameter, which could be easily refactored and changed by mistake by another developer.
It's more convenient to choose a more visible approach to express our intention to inject a specific bean: using the `@Qualifier` annotation.

*b)* You can explicitly select a specific bean using the `@Qualifier` annotation.

Example: [02-dependency-injection-i](02-dependency-injection-i/)

```java
@Configuration
public class ProjectConfig {

    @Bean
    public Parrot parrot1() {
        return new Parrot("Koko");
    }

    @Bean
    public Parrot parrot2() {
        return new Parrot("Miki");
    }

    @Bean
    public Person person(@Qualifier("parrot2") Parrot parrot) {
        return new Person("Ella", parrot);
    }
}
```

*c)* If none of the beans is primary and you don’t use `@Qualifier`, the app will fail with an exception, complaining that the context contains more beans of the same type and Spring doesn’t know which one to choose.
The exception thrown is `NoUniqueBeanDefinitionException`.
