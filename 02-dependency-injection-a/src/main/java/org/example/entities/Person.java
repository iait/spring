package org.example.entities;

public class Person {

    private static int count;

    private String name;
    private Parrot parrot;

    public Person(String name, Parrot parrot) {
        this.name = name;
        this.parrot = parrot;
        System.out.println("Person " + (++count) + " created");
    }

    public String getName() {
        return name;
    }

    public Parrot getParrot() {
        return parrot;
    }
}
