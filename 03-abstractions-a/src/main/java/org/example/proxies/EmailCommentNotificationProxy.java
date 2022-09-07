package org.example.proxies;

import org.example.model.Comment;

public class EmailCommentNotificationProxy implements CommentNotificationProxy {

    @Override
    public void sendComment(Comment comment) {
        System.out.println("Sending notification by email for comment: " + comment.getText());
    }
}
