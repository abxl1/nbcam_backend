package com.sparta.schedulerjpa.dto.scheduleDto.responseDto;

import com.sparta.schedulerjpa.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleUpdateResponseDto {

    private final String scheduleUserName;
    private final String scheduleTitle;
    private final String scheduleContents;

    public ScheduleUpdateResponseDto(Schedule addSchedule) {
        this.scheduleUserName = addSchedule.getScheduleUserName();
        this.scheduleTitle = addSchedule.getScheduleTitle();
        this.scheduleContents = addSchedule.getScheduleContents();
    }
}
