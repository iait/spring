package org.example.services;

import org.example.aspects.ToLog;
import org.example.model.Comment;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CommentService {

    private Logger logger = Logger.getLogger(CommentService.class.getName());

    @ToLog
    public void publishComment(Comment comment) {
        logger.info("Publishing comment: " + comment.getText());
    }
}
