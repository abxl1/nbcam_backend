package com.sparta.newsfeed.profile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.newsfeed.profile.entity.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RequestUserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String nickname;
    private String bio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;

    private String inputPassword;
    private String editPassword;
}
