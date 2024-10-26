package com.sparta.trelloproject.domain.card.dto.response;

import com.sparta.trelloproject.domain.card.entity.Card;
import lombok.Getter;

@Getter
public class CardSaveResponse {

    private Long id;
    private final String title;
    private final String description;

    public CardSaveResponse(Card card){
        this.id = card.getId();
        this.title = card.getTitle();
        this.description = card.getDescription();
    }

}
