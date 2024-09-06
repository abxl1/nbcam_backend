package com.sparta.schedulerjpa.dto.scheduleDto.responseDto;

import com.sparta.schedulerjpa.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleSaveResponseDto {

    private final Long scheduleId;
    private final String scheduleUserName;
    private final String scheduleTitle;
    private final String scheduleContents;

    public ScheduleSaveResponseDto(Schedule addSchedule) {
        this.scheduleId = addSchedule.getScheduleId();
        this.scheduleUserName = addSchedule.getScheduleUserName();
        this.scheduleTitle = addSchedule.getScheduleTitle();
        this.scheduleContents = addSchedule.getScheduleContents();
    }
}
