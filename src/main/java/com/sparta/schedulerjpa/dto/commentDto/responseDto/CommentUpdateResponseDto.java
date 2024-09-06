package com.sparta.schedulerjpa.dto.commentDto.responseDto;

import com.sparta.schedulerjpa.entity.Comment;
import lombok.Getter;

@Getter
public class CommentUpdateResponseDto {

    private final String commentUserName;
    private final String commentContents;

    public CommentUpdateResponseDto(Comment updateComment) {
        this.commentUserName = updateComment.getCommentUserName();
        this.commentContents = updateComment.getCommentContents();
    }
}
