package com.sparta.board.dto;

public class BoardSaveResponseDto {

    private final String title;
    private final String contents;

    public BoardSaveResponseDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
