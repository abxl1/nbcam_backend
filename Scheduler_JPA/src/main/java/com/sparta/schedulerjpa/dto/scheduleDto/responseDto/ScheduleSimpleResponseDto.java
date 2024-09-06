package com.sparta.schedulerjpa.dto.scheduleDto.responseDto;

import lombok.Getter;

@Getter
public class ScheduleSimpleResponseDto {

    private final String scheduleUserName;
    private final String scheduleTitle;
    private final String scheduleContents;

    public ScheduleSimpleResponseDto(String scheduleUserName, String scheduleTitle, String scheduleContents) {
        this.scheduleUserName = scheduleUserName;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContents = scheduleContents;
    }
}
