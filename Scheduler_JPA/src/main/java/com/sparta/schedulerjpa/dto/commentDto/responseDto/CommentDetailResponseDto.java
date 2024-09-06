package com.sparta.schedulerjpa.dto.commentDto.responseDto;

import com.sparta.schedulerjpa.entity.Comment;
import com.sparta.schedulerjpa.entity.Schedule;
import lombok.Getter;

@Getter
public class CommentDetailResponseDto {

    private final String commentUserName;
    private final String commentContents;

    public CommentDetailResponseDto(Comment getComment) {
        this.commentUserName = getComment.getCommentUserName();
        this.commentContents = getComment.getCommentContents();
    }
}
