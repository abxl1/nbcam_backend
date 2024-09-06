package com.sparta.newsfeed.post.dto.commentDto;

import com.sparta.newsfeed.post.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private final String commentContents;

    public CommentResponseDto(Comment comment) {
        this.commentContents = comment.getCommentContents();
    }
}
