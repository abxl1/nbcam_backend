package com.sparta.board.dto;

import lombok.Getter;

@Getter
public class BoardTiltleUpdateResponseDto {
    private final Long id;
    private final String title;

    public BoardTiltleUpdateResponseDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}