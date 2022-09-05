package org.example.entities;

public class Parrot {

    private static int count;

    private String name;

    public Parrot(String name) {
        this.name = name;
        System.out.println("Parrot " + (++count) + " created");
    }

    public String getName() {
        return name;
    }
}
