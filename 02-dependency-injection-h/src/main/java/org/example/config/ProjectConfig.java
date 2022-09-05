package org.example.config;

import org.example.entities.Parrot;
import org.example.entities.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ProjectConfig {

    @Bean
    @Primary
    public Parrot parrot1() {
        return new Parrot("Koko");
    }

    @Bean
    public Parrot parrot2() {
        return new Parrot("Miki");
    }

    @Bean
    public Person person(Parrot p) {
        return new Person("Ella", p);
    }
}
