package com.sparta.scheduler.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private String schedule;
    private String username;
    private String password;
    private String date;
}
