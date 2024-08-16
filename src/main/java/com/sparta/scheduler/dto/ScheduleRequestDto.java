package com.sparta.scheduler.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private Long id;
    private String schedule;
    private String username;
    private String password;
    private String created_at;
    private String updated_at;
}
