package com.sparta.schedulerjpa.dto.commentDto.responseDto;

import lombok.Getter;

@Getter
public class CommentSimpleResponseDto {

    private final String commentUserName;
    private final String commentContents;

    public CommentSimpleResponseDto(String commentUserName, String commentContents) {
        this.commentUserName = commentUserName;
        this.commentContents = commentContents;
    }
}
