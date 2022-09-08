package org.example;

import org.example.config.ProjectConfig;
import org.example.model.Comment;
import org.example.services.CommentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var service = context.getBean(CommentService.class);

        var comment = new Comment("John", "Hello World");
        service.publishComment(comment);
    }
}
