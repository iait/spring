package org.example.config;

import org.example.entities.Parrot;
import org.example.entities.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {

     @Bean
    public Parrot parrot() {
         return new Parrot("Koko");
     }

     @Bean
     public Person person() {
         return new Person("Ella", parrot());
     }
}
