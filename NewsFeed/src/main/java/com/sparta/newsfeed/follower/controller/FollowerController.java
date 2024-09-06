package com.sparta.newsfeed.follower.controller;

import com.sparta.newsfeed.auth.annotaion.Auth;
import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.follower.dto.requestDto.FollowerRequestDto;
import com.sparta.newsfeed.follower.dto.responseDto.FollowAcceptResponseDto;
import com.sparta.newsfeed.follower.dto.responseDto.FollowerGetResponseDto;
import com.sparta.newsfeed.follower.dto.responseDto.FollowerSaveResponseDto;
import com.sparta.newsfeed.follower.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowerController {

    private final FollowerService followerService;

    // post - 친구 신청 보내기
    @PostMapping("/users/followers")
    public ResponseEntity<FollowerSaveResponseDto> saveFollower(@Auth AuthUser authUser, @RequestBody FollowerRequestDto followerSaveRequestDto) {
        return ResponseEntity.ok(followerService.saveFollower(authUser, followerSaveRequestDto));
    }

    // post - 친구 신청 수락하기
    @PostMapping("/users/followers/accept")
    public ResponseEntity<FollowAcceptResponseDto> acceptFollow(@Auth AuthUser authUser, @RequestBody FollowerRequestDto followerRequestDto) {
        return ResponseEntity.ok(followerService.acceptFollow(authUser, followerRequestDto));
    }

    // delete - 친구 신청 거절하기
    @DeleteMapping("/users/followers/denied")
    public void deniedFollow(@Auth AuthUser authUser, @RequestBody FollowerRequestDto followerRequestDto) {
        followerService.deniedFollow(authUser, followerRequestDto);
    }

    // get - 친구 신청 들어온 목록 조회하기
    @GetMapping("/users/followers")
    public ResponseEntity<List<FollowerGetResponseDto>> getFollowList(@Auth AuthUser authUser) {
        return ResponseEntity.ok(followerService.getFollowList(authUser));
    }

    // delete - 친구 관계 끊기
    @DeleteMapping("/users/followers/delete")
    public void deleteFollower(@Auth AuthUser authUser, @RequestBody FollowerRequestDto followerRequestDto) {
        followerService.deleteFollower(authUser, followerRequestDto);
    }
}