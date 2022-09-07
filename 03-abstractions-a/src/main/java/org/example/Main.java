package org.example;

import org.example.model.Comment;
import org.example.proxies.EmailCommentNotificationProxy;
import org.example.repositories.DatabaseCommentRepository;
import org.example.servicies.CommentService;

public class Main {

    public static void main(String[] args) {

        var commentRepository = new DatabaseCommentRepository();
        var commentNotificationProxy = new EmailCommentNotificationProxy();

        var commentService = new CommentService(commentRepository, commentNotificationProxy);

        var comment = new Comment("John", "Hello World");

        commentService.publishComment(comment);
    }
}
