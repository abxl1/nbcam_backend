package com.sparta.schedulerjpa.dto.commentDto.requestDto;

import lombok.Getter;

@Getter
public class CommentSaveRequestDto {

    private String commentUserName;
    private String commentContents;
}
