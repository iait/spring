package org.example;

import org.example.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    static public void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
    }
}
