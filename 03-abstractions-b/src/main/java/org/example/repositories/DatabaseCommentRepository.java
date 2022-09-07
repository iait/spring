package org.example.repositories;

import org.example.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseCommentRepository implements CommentRepository {

    @Override
    public void storeComment(Comment comment) {
        System.out.println("Storing in database comment: " + comment.getText());
    }
}
