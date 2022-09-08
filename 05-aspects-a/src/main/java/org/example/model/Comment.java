package org.example.model;

public class Comment {

    private final String author;
    private final String text;

    public Comment(String author, String text) {
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }
}
