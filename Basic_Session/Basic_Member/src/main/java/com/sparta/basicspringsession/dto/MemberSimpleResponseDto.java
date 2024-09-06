package com.sparta.basicspringsession.dto;

import lombok.Getter;

@Getter
public class MemberSimpleResponseDto {
    private final Long id;
    private final String name;

    public MemberSimpleResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}