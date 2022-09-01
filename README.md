# Spring

[Spring Projects](https://spring.io/projects)

Spring is an application framework that is part of the Java ecosystem.
An application framework is a set of common software functionalities that provides a foundation structure for developing an application.
Common application functionalities include logging, transactions, security, data persistence, data transfer, catching, and so one.
Instead of building a solution from scratch, you can take advantage of the mature code of an application framework like Spring and only develop the business logic related to your specific problem or need.

We refer to Spring as a framework, but it is much more complex. 
Spring is an ecosystem of frameworks.
Some of them are:

* **Spring Core**.
The fundamental part of Spring that includes the Spring context, inversion of control (IoC), Spring aspect-oriented programming (AOP), Spring Expression Language (SpEL).
* **Spring Model-View-Controller (MVC)**.
Enables you to develop web applications that serve HTTP requests.
* **Spring Data Access**.
Provides basic tools you can use to connect to SQL databases to implement the persistence layer of your app.
* **Spring Testing**.
Tools you need to write unit and integration tests for your Spring application.

## Inversion of Control

When using this principle, instead of allowing the app to control the execution, we give control to some other piece of software—in our case, the Spring framework.
Through configurations, we instruct the framework on how to manage the code we write, which defines the logic of the app.

In this context the term *controls* refers to actions like *creating an instance* or *calling a method*. 
A framework can create objects of the classes you define in your app.
Based on the configurations that you write, Spring intercepts the method to augment it with various features.

## Spring Data

We have **Spring Data Access**, which is a module of Spring Core, and we also have an independent project in the Spring ecosystem named **Spring Data**.
Spring Data Access contains fundamental data access implementations like the transaction mechanism and JDBC tools.
Spring Data enhances access to databases and offers a broader set of tools, which makes development more accessible and enables your app to connect to different kinds of data sources both SQL and NoSQL.

The Spring Data project implements a part of the Spring ecosystem that enables you to easily connect to databases and use the persistence layer with a minimum number of lines of code written.

JDBC.

Object-relational mapping (ORM) frameworks like Hibernate.
This is an ORM framework allows you to treat the tables and their relationships in a database as objects and relationships among objects.

## Spring MVC

To develop web apps.

## Spring Boot

Spring Boot is a project part of the Spring ecosystem that introduces the concept of **convention over configuration**.
The main idea of this concept is that instead of setting up all the configurations of a framework yourself, Spring Boot offers you a default configuration that you can customize as needed.
The result, in general, is that you write less code because you follow known conventions and your app differs from others in few or small ways.
So instead of writing all the configurations for each and every app, it’s more efficient to start with a default configuration and only change what’s different from the convention.

Goals:
* Easy to create and configure apps
* Automatic configuration based on classpath