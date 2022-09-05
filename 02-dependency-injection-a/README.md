# Dependency Injection

We establish the relationship between the person and the parrot by directly calling the method that returns the bean we want to set.

When two methods annotated with `@Bean` call each other, Spring knows you want to create a link between the beans.
If the bean already exists in the context, Spring returns the existing bean without forwarding the call to the `@Bean` method.
If the bean doesn’t exist, Spring creates the bean, adds it to the context, and returns its reference.