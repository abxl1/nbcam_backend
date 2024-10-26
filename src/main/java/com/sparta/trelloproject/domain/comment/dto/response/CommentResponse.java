package com.sparta.trelloproject.domain.comment.dto.response;

import lombok.Getter;

@Getter
public class CommentResponse {
    private final Long commentId;
    private final String text;
    private final String userEmail;

    public CommentResponse(Long commentId, String text, String userEmail) {
        this.commentId = commentId;
        this.text = text;
        this.userEmail = userEmail;
    }
}