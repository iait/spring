# Dependency Injection

Instead of directly calling the method that defines the bean we wish to refer to, we add a parameter to the method of the corresponding type of object, and we rely on Spring to provide us a value through that parameter.
With this approach, it doesn't matter if the bean we want to refer to is defined with a method annotated with `@Bean` or using a stereotype annotation like `@Component`.

By defining a parameter to the method, we instruct Spring to provide us a bean of the type of that parameter from its context.