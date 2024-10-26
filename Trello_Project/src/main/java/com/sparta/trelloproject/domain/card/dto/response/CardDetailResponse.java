package com.sparta.trelloproject.domain.card.dto.response;

import com.sparta.trelloproject.domain.comment.dto.response.CommentResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class CardDetailResponse {

    private String title;
    private String description;
    private List<CardActivityResponse> activities;
    private List<CommentResponse> comments;

    public CardDetailResponse(String title, String description, List<CardActivityResponse> activities, List<CommentResponse> comments) {
        this.title = title;
        this.description = description;
        this.activities = activities;
        this.comments = comments;
    }
}
