package org.example.entities;

import org.springframework.stereotype.Component;

@Component
public class Person {

    private static int count;

    private String name;

    private final Parrot parrot;

    public Person(Parrot parrot) {
        this.parrot = parrot;
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
