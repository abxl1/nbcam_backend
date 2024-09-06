package com.sparta.schedulerjpa.dto.commentDto.responseDto;

import com.sparta.schedulerjpa.entity.Comment;
import lombok.Getter;

@Getter
public class CommentSaveResponseDto {
    private final Long commentId;
    private final String commentUserName;
    private final String commentContents;

    public CommentSaveResponseDto(Comment addComment) {
        this.commentId = addComment.getCommentId();
        this.commentUserName = addComment.getCommentUserName();
        this.commentContents = addComment.getCommentContents();
    }
}
