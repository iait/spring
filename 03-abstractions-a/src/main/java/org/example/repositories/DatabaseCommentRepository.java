package org.example.repositories;

import org.example.model.Comment;

public class DatabaseCommentRepository implements CommentRepository {

    @Override
    public void storeComment(Comment comment) {
        System.out.println("Storing in database comment: " + comment.getText());
    }
}
