package com.sparta.schedulerjpa.dto.scheduleDto.requestDto;

import lombok.Getter;

@Getter
public class ScheduleUpdateRequestDto {

    private String scheduleUserName;
    private String scheduleTitle;
    private String scheduleContents;

}
