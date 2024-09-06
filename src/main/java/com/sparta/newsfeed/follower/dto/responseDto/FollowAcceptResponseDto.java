package com.sparta.newsfeed.follower.dto.responseDto;

import com.sparta.newsfeed.follower.entity.Follower;
import com.sparta.newsfeed.profile.entity.User;
import lombok.Getter;

@Getter
public class FollowAcceptResponseDto {

    private final String followerMail;
    private final String status;

    public FollowAcceptResponseDto(User user, Follower.Status status) {
        this.followerMail = user.getEmail();
        this.status = status.toString();
    }
}
