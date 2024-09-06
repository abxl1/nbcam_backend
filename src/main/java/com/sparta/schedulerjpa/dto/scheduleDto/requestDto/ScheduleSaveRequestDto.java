package com.sparta.schedulerjpa.dto.scheduleDto.requestDto;

import lombok.Getter;

@Getter
public class ScheduleSaveRequestDto {

    private String scheduleUserName;
    private String scheduleTitle;
    private String scheduleContents;
}
