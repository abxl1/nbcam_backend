package com.sparta.basicspringsession.dto;

import lombok.Getter;

@Getter
public class MemberUpdateResponseDto {
    private final Long id;
    private final String name;

    public MemberUpdateResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
