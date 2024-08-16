package com.sparta.scheduler.dto;

import com.sparta.scheduler.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String schedule;
    private String username;
    private String created_at;
    private String updated_at;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.schedule = schedule.getSchedule();
        this.username = schedule.getUsername();
        this.created_at = schedule.getCreated_at();
        this.updated_at = schedule.getUpdated_at();
    }

    public ScheduleResponseDto(Long id, String username, String schedule, String created_at, String updated_at) {
        this.id = id;
        this.username = username;
        this.schedule = schedule;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

}
