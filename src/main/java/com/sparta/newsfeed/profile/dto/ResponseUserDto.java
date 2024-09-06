package com.sparta.newsfeed.profile.dto;

import com.sparta.newsfeed.profile.entity.Gender;
import com.sparta.newsfeed.profile.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUserDto {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String nickname;
    private String bio;
    private Date birthday;

    public ResponseUserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.nickname = user.getNickname();
        this.bio = user.getBio();
        this.birthday = user.getBirthday();
    }
}
