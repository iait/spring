# Bean scopes and life cycle

## Singleton Beans

The **singleton** bean scope defines Spring’s default approach for managing the beans in its context.

Spring creates a singleton bean when assigns the bean a name and loads it to the context.
We name this scope singleton because you always get the same instance when you refer to a specific bean.
**But you can have more instances of the same type in the Spring context if they have different names**.

With the classical singleton pattern you have only one instance of a type in the app.
But for Spring, the singleton concept allows multiple instances of the same type, and singleton means unique per name but not unique per app.

Because the singleton bean scope assumes that multiple components of the app can share an object instance, the most important thing to consider is that these beans should usually be immutable.
Most often, a real-world app executes actions on multiple threads. 
In such a scenario, multiple threads share the same object instance. 
If these threads change the instance, you might encounter a *race-condition* scenario.

A race condition is a situation that can happen in multithreaded architectures when multiple threads try to change a shared resource. 
In case of a race condition, the developer needs to properly synchronize the threads to avoid unexpected execution results or errors.

If you want mutable singleton beans, you need to make these beans concurrent by yourself (mainly by employing thread synchronization). 
But singleton beans aren’t designed to be synchronized. 
They’re commonly used to define an app’s backbone class design and delegate responsibilities one to another. 
Technically, synchronization is possible, but it’s not a good practice.

One of the advantages of constructor injection is that it allows you to make the instance immutable (define the bean’s fields as final).

Key points:
* Make an object bean in the Spring context only if you need Spring to manage it so that the framework can augment that bean with a specific capability. If the object doesn’t need any capability offered by the framework, you don’t need to make it a bean.
* If you need to make an object bean in the Spring context, it should be singleton only if it’s immutable. Avoid designing mutable singleton beans.
* If a bean needs to be mutable, an option could be to use the prototype scope.

## Eager and Lazy instantiation

In most cases, Spring creates all singleton beans when it initializes the context—this is Spring’s default behavior, which is also called **eager** instantiation.
With **lazy** instantiation, Spring doesn’t create the singleton instances when it creates the context. 
Instead, it creates each instance the first time someone refers to the bean.

In most cases, it’s more comfortable to let the framework create all the instances at the beginning when the context is instantiated (eager); this way, when one instance delegates to another, the second bean already exists in any situation.

In a lazy instantiation, the framework has to first check if the instance exists and eventually create it if it doesn’t, so from the performance point of view, it’s better to have the instances in the context already (eager) because it spares some checks the framework needs to do when one bean delegates to another. 
Another advantage of eager instantiation is when something is wrong and the framework cannot create a bean; we can observe this issue when starting the app. 
With lazy instantiation, someone would observe the issue only when the app is already executing and it reaches the point that the bean needs to be created.

TODO example

## Prototype Beans

Every time you request a reference to a prototype-scoped bean, Spring creates a new object instance. 
For prototype beans, Spring doesn’t create and manage an object instance directly. 
The framework manages the object’s type and creates a new instance every time someone requests a reference to the bean.

We use the `@Scope` annotation to change the bean scope to prototype.

With prototype beans, we usually don't have concurrency problems because each thread that requests the bean gets a different instance, so defining mutable prototype beans might not be a problem in this case.

An example can be found in [04-scopes](04-scopes/).

```java
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommentProcessor {
    // ...
}
```

## Summary

Spring offers two bean scopes: **singleton** and **prototype**.

| **Singleton**                                                          | **Prototype**                                                |
|------------------------------------------------------------------------|--------------------------------------------------------------|
| The framework associates a name with an actual object instance.        | A name is associated with a type.                            |
| Every time you refer to a bean name you’ll get the same instance.      | Every time you refer to a bean name, you get a new instance. |
| Can create the instance when context is loaded or when first referred. | Instances are always created when you refer to the bean.     |
| Singleton is the default bean scope in Spring.                         | You need to explicitly mark a bean as a prototype.           |
| It’s not recommended that a singleton bean to have mutable attributes. | A prototype bean can have mutable attributes.                |

With singleton, Spring manages the object instances directly in its context.
Each instance has a unique name, and using that name you always refer to that specific instance. 
Singleton is Spring’s default.

With prototype, Spring considers only the object type. 
Each type has a unique name associated with it. 
Spring creates a new instance of that type every time you refer to the bean name.

You can configure Spring to create a singleton bean either when the context is initialized (**eager**) or when the bean is referred for the first time (**lazy**). 
By default, a bean is eagerly instantiated.

In apps, we most often use singleton beans. Because anyone referring to the same name gets the same object instance, multiple different threads could access and use this instance. 
For this reason, it’s advisable to have the instance **immutable**. 
If, however, you prefer to have mutating operations on the bean’s attribute, it’s your responsibility to take care of the thread synchronization.
If you need to have a **mutable** object like a bean, using the prototype scope could be a good option.

Be careful with injecting a prototype-scoped bean into a singleton-scoped bean. 
When you do something like this, you need to be aware that the singleton instance always uses the same prototype instance, which Spring injects when it creates the singleton instance. 
This is usually a vicious design because the point of making a bean prototype-scoped is to get a different instance for every use.