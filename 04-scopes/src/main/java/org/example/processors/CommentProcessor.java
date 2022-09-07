package org.example.processors;

import org.example.model.Comment;
import org.example.repositories.CommentRepository;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommentProcessor {

    private static int count;

    private final CommentRepository repository;

    public CommentProcessor(CommentRepository repository) {
        System.out.println("CommentProcessor instance: " + ++count);
        this.repository = repository;
    }

    private Comment comment;

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void processComment() {
        comment = new Comment(comment.getAuthor(), comment.getText() + " processed by " + count);
    }

    public boolean validateComment() {
        return comment.getAuthor() != null && !comment.getAuthor().isEmpty() &&
                comment.getText() != null && !comment.getText().isEmpty();
    }
}
