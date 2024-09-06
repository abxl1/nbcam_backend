package com.sparta.newsfeed.auth.dto;

import com.sparta.newsfeed.profile.entity.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserRequestDto {
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String nickName;
    private String imageUrl;
    private Gender gender;
    private String bio;
    private Date birthDay;
}
