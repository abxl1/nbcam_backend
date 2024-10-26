package com.sparta.trelloproject.domain.board.dto.request;

import lombok.Getter;

@Getter
public class BoardRequest {
    private final String title;
    private final String background;

    public BoardRequest(String title, String background) {
        this.title = title;
        this.background = background;
    }
}
