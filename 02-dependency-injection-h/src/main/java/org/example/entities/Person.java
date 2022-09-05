package org.example.entities;

import org.springframework.beans.factory.annotation.Autowired;

public class Person {

    private String name;
    private Parrot parrot;

    public Person(String name, Parrot parrot) {
        this.name = name;
        this.parrot = parrot;
    }

    public String getName() {
        return name;
    }

    public Parrot getParrot() {
        return parrot;
    }
}
