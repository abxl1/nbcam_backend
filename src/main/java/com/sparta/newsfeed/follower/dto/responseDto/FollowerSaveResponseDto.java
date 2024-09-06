package com.sparta.newsfeed.follower.dto.responseDto;

import com.sparta.newsfeed.follower.entity.Follower;
import lombok.Getter;

@Getter
public class FollowerSaveResponseDto {
    private final String followerEmail;
    private final String userEmail;
    private final String successMsg;

    public FollowerSaveResponseDto(Follower saveFollower) {
        this.followerEmail = saveFollower.getFollower().getEmail();
        this.userEmail = saveFollower.getUser().getEmail();
        this.successMsg = "친구 신청이 완료되었습니다.";
    }
}
