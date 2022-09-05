package org.example.config;

import org.example.entities.Parrot;
import org.example.entities.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

    @Bean
    public Parrot parrot1() {
        return new Parrot("Koko");
    }

    @Bean
    public Parrot parrot2() {
        return new Parrot("Miki");
    }

    @Bean
    public Person person(Parrot parrot2) {
        return new Person("Ella", parrot2);
    }
}
