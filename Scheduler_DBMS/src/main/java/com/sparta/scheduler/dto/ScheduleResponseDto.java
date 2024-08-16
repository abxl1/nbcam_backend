package com.sparta.scheduler.dto;

import com.sparta.scheduler.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String schedule;
    private String username;
    private String date;
    
    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.schedule = schedule.getSchedule();
        this.username = schedule.getUsername();
        this.date = schedule.getDate();
    }

    public ScheduleResponseDto(Long id, String username, String schedule, String date) {
        this.id = id;
        this.username = username;
        this.schedule = schedule;
        this.date = date;
    }
}
