package org.example.config;

import org.example.animals.Cat;
import org.example.animals.Parrot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.example.animals")
public class ProjectConfig {

    @Bean
    Parrot parrot() {
        var p = new Parrot();
        p.setName("Koko");
        return p;
    }

    @Bean
    String hello() {
        return "hello";
    }

    @Bean
    Integer ten() {
        return 10;
    }

    @Bean
    Cat cat1() {
        return new Cat("Oscar");
    }

    @Bean
    Cat cat2() {
        return new Cat("Tiger");
    }

    @Bean
    Cat cat3() {
        return new Cat("Kitty");
    }
}
