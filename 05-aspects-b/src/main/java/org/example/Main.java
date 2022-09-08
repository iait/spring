package org.example;

import org.example.config.ProjectConfig;
import org.example.model.Comment;
import org.example.services.CommentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var service = context.getBean(CommentService.class);
        String value = service.publishComment(new Comment("John", "Hello World"));

        logger.info(value);
    }
}
