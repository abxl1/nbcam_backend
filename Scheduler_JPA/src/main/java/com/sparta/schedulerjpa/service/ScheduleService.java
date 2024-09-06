package com.sparta.schedulerjpa.service;

import com.sparta.schedulerjpa.dto.scheduleDto.requestDto.ScheduleSaveRequestDto;
import com.sparta.schedulerjpa.dto.scheduleDto.requestDto.ScheduleUpdateRequestDto;
import com.sparta.schedulerjpa.dto.scheduleDto.responseDto.ScheduleDetailResponseDto;
import com.sparta.schedulerjpa.dto.scheduleDto.responseDto.ScheduleSaveResponseDto;
import com.sparta.schedulerjpa.dto.scheduleDto.responseDto.ScheduleSimpleResponseDto;
import com.sparta.schedulerjpa.dto.scheduleDto.responseDto.ScheduleUpdateResponseDto;
import com.sparta.schedulerjpa.entity.Schedule;
import com.sparta.schedulerjpa.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // post
    @Transactional
    public ScheduleSaveResponseDto saveSchedule(ScheduleSaveRequestDto scheduleSaveRequestDto) {
        Schedule schedule = new Schedule(
                scheduleSaveRequestDto.getScheduleUserName(),
                scheduleSaveRequestDto.getScheduleTitle(),
                scheduleSaveRequestDto.getScheduleContents()
        );
        Schedule addSchedule = scheduleRepository.save(schedule);
        return new ScheduleSaveResponseDto(addSchedule);
    }

    // get page ( 1page - 10size )
    public Page<ScheduleSimpleResponseDto> getSchedules(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Schedule> schedules = scheduleRepository.findAllByOrderByModifiedAtDesc(pageable);

        return schedules.map(schedule -> new ScheduleSimpleResponseDto(
                schedule.getScheduleUserName(),
                schedule.getScheduleTitle(),
                schedule.getScheduleContents()
        ));
    }

    // get one
    public ScheduleDetailResponseDto detailSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new NullPointerException("해당 일정이 없습니다."));
        return new ScheduleDetailResponseDto(schedule);
    }

    // put
    @Transactional
    public ScheduleUpdateResponseDto updateSchedule(Long scheduleId, ScheduleUpdateRequestDto scheduleUpdateRequestDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new NullPointerException("해당 일정이 없습니다."));
        schedule.scheduleUpdate(scheduleUpdateRequestDto.getScheduleUserName(), scheduleUpdateRequestDto.getScheduleTitle(), scheduleUpdateRequestDto.getScheduleContents());
        return new ScheduleUpdateResponseDto(schedule);
    }

    // delete
    @Transactional
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new NullPointerException("해당 일정이 없습니다."));
        scheduleRepository.delete(schedule);
    }
}
