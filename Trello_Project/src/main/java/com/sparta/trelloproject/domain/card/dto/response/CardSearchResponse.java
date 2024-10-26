package com.sparta.trelloproject.domain.card.dto.response;

import com.sparta.trelloproject.domain.card.entity.Card;
import lombok.Getter;

@Getter
public class CardSearchResponse {

    private final String title;
    private final String description;
    private final Long boardId;
    private final Long listId;

    public CardSearchResponse(Card card) {
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.boardId = card.getTaskList().getBoard().getId();
        this.listId = card.getTaskList().getId();
    }
}
