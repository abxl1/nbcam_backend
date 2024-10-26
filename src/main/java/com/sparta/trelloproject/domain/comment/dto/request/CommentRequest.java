package com.sparta.trelloproject.domain.comment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequest {
    private String text;

    public CommentRequest(String text) {
        this.text = text;

    }
}
