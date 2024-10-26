package com.sparta.trelloproject.domain.card.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ViewCountSearchResponse {
    private final String viewCount;
}