package org.example.repositories;

import org.example.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

    public void storeComment(Comment comment) {
        System.out.println("Comment stored: " + comment.getText());
    }
}
