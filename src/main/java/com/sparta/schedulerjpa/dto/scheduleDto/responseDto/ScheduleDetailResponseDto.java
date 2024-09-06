package com.sparta.schedulerjpa.dto.scheduleDto.responseDto;

import com.sparta.schedulerjpa.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleDetailResponseDto {

    private final String scheduleTitle;
    private final String scheduleContents;

    public ScheduleDetailResponseDto(Schedule detailSchedule) {
        this.scheduleTitle = detailSchedule.getScheduleTitle();
        this.scheduleContents = detailSchedule.getScheduleContents();
    }
}
