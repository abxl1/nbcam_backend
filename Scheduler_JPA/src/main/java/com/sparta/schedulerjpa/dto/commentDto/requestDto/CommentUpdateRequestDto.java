package com.sparta.schedulerjpa.dto.commentDto.requestDto;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    private String commentUserName;
    private String commentContents;
}
