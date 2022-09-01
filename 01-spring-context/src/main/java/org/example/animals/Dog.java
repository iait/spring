package org.example.animals;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Dog {

    private String name;

    @PostConstruct
    public void init() {
        name = "Buddy";
    }

    public String getName() {
        return name;
    }
}
