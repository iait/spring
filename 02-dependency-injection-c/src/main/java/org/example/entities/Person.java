package org.example.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Person {

    private static int count;

    private String name;

    @Autowired
    private Parrot parrot;

    public Person() {
        System.out.println("Person " + (++count) + " created");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parrot getParrot() {
        return parrot;
    }
}
