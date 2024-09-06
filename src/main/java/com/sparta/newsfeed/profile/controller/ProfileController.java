package com.sparta.newsfeed.profile.controller;

import com.sparta.newsfeed.auth.annotaion.Auth;
import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.profile.dto.RequestUserDto;
import com.sparta.newsfeed.profile.dto.ResponseUserDto;
import com.sparta.newsfeed.profile.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/getinfo")
    public ResponseUserDto getprofile(@Auth AuthUser authUser, @RequestParam Long targatId){
        Long userId = authUser.getId();
        return profileService.getprofile(userId, targatId);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseUserDto> updateprofile(@Auth AuthUser authUser, @RequestBody RequestUserDto requestDto){
        Long userId = authUser.getId();
        return ResponseEntity.ok(profileService.updateprofile(userId,requestDto));
    }
}
