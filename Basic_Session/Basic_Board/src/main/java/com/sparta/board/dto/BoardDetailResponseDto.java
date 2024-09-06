package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class BoardDetailResponseDto {
    private final String title;
    private final String contents;

    public BoardDetailResponseDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    // id 만 전달해 주면 response로 제목과 내용 응답?
}
