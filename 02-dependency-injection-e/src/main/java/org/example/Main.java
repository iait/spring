package org.example;

import org.example.config.ProjectConfig;
import org.example.entities.Parrot;
import org.example.entities.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static public void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Parrot parrot = context.getBean(Parrot.class);
        parrot.setName("Koko");

        Person person = context.getBean(Person.class);
        person.setName("Ella");

        System.out.println("Parrot's name: " + parrot.getName());
        System.out.println("Person's name: " + person.getName());
        System.out.println("Person's parrot: " + person.getParrot().getName());
        System.out.println(person.getParrot() == parrot);
    }
}
