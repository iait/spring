package org.example.services;

import org.example.model.Comment;
import org.example.processors.CommentProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final ApplicationContext context;

    public CommentService(ApplicationContext context) {
        this.context = context;
    }

    public void publishComment(Comment comment) {

        var processor = context.getBean(CommentProcessor.class);
        processor.setComment(comment);
        if (!processor.validateComment()) {
            throw new IllegalArgumentException();
        }
        processor.processComment();
        var processedComment = processor.getComment();
        System.out.println("Published comment: " + processedComment.getText());
    }
}
