package org.example;

import org.example.config.ProjectConfig;
import org.example.entities.Parrot;
import org.example.entities.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static public void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Parrot parrot1 = context.getBean("parrot1", Parrot.class);
        Parrot parrot2 = context.getBean("parrot2", Parrot.class);
        Person person = context.getBean(Person.class);

        System.out.println("Parrot1's name: " + parrot1.getName());
        System.out.println("Parrot2's name: " + parrot2.getName());
        System.out.println("Person's name: " + person.getName());
        System.out.println("Person's parrot: " + person.getParrot().getName());
        System.out.println(person.getParrot() == parrot1);
    }
}
