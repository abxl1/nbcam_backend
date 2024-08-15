package com.sparta.scheduler.entity;

import com.sparta.scheduler.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    private Long id; // 스케줄 구분
    private String username; // 스케쥴 작성자
    private String schedule; // 스케쥴 내용
    private String password; // 스케쥴 비밀번호
    private String date; // 스케쥴 작성일(수정일)

    public Schedule(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.schedule = requestDto.getSchedule();
        this.password = requestDto.getPassword();
        this.date = requestDto.getDate();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.schedule = requestDto.getSchedule();
        this.date = requestDto.getDate();
    }
}