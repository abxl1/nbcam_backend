package com.sparta.schedulerjpa.controller;

import com.sparta.schedulerjpa.dto.scheduleDto.requestDto.ScheduleSaveRequestDto;
import com.sparta.schedulerjpa.dto.scheduleDto.requestDto.ScheduleUpdateRequestDto;
import com.sparta.schedulerjpa.dto.scheduleDto.responseDto.ScheduleDetailResponseDto;
import com.sparta.schedulerjpa.dto.scheduleDto.responseDto.ScheduleSaveResponseDto;
import com.sparta.schedulerjpa.dto.scheduleDto.responseDto.ScheduleSimpleResponseDto;
import com.sparta.schedulerjpa.dto.scheduleDto.responseDto.ScheduleUpdateResponseDto;
import com.sparta.schedulerjpa.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // 생성자로 ScheduleService에 Bean 주입
public class ScheduleController {

    public final ScheduleService scheduleService;

    // post
    @Transactional
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleSaveResponseDto> saveSchedule(@RequestBody ScheduleSaveRequestDto scheduleSaveRequestDto) {
        return ResponseEntity.ok(scheduleService.saveSchedule(scheduleSaveRequestDto));
    }

    // get page ( 1page - 10size )
    @GetMapping("/schedules")
    public ResponseEntity<Page<ScheduleSimpleResponseDto>> getSchedules(
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(scheduleService.getSchedules(page, size));
    }

    // get one
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleDetailResponseDto> getSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.detailSchedule(scheduleId));
    }

    // put
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponseDto> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleUpdateRequestDto scheduleUpdateRequestDto) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, scheduleUpdateRequestDto));
    }

    // delete
    @DeleteMapping("/schedules/{scheduleId}")
    public void deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }
}
