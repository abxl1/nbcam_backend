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
    private String created_at; // 스케쥴 작성일
    private String updated_at; // 스케쥴 수정일

    public Schedule(ScheduleRequestDto requestDto) {
        this.id = requestDto.getId();
        this.username = requestDto.getUsername();
        this.schedule = requestDto.getSchedule();
        this.password = requestDto.getPassword();
        this.created_at = requestDto.getCreated_at();
        this.updated_at = requestDto.getUpdated_at();
    }
}