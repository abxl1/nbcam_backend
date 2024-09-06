package com.sparta.basicspringsession.dto;

import lombok.Getter;

@Getter
public class MemberDetailResponseDto {
    private final Long id;
    private final String name;

    public MemberDetailResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}