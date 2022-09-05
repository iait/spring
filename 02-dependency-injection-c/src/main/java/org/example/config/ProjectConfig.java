package org.example.config;

import org.example.entities.Parrot;
import org.example.entities.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example.entities")
public class ProjectConfig {
}
