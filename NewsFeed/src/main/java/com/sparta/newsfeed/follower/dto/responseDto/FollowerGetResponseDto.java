package com.sparta.newsfeed.follower.dto.responseDto;

import lombok.Getter;

@Getter
public class FollowerGetResponseDto {
    private final String email;

    public FollowerGetResponseDto(String email) {
        this.email = email;
    }
}
