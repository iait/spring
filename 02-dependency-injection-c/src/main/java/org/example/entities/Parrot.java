package org.example.entities;

import org.springframework.stereotype.Component;

@Component
public class Parrot {

    private static int count;

    private String name;

    public Parrot() {
        System.out.println("Parrot " + (++count) + " created");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
