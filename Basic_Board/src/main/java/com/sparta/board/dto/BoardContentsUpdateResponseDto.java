package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class BoardContentsUpdateResponseDto {
    private final Long id;
    private final String contents;

    public BoardContentsUpdateResponseDto(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }
}