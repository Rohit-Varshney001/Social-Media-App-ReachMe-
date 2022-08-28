package com.example.reachme.Model;

public class Comment {
    private String commentBody;
    private String commentedBy;
    private Long CommentedAt;

    public Comment() {
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }

    public Long getCommentedAt() {
        return CommentedAt;
    }

    public void setCommentedAt(Long commentedAt) {
        CommentedAt = commentedAt;
    }

}
