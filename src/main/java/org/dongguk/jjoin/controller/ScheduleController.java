package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.dto.request.ScheduleDecideDto;
import org.dongguk.jjoin.dto.response.ScheduleDayDto;
import org.dongguk.jjoin.dto.response.ScheduleDaysDto;
import org.dongguk.jjoin.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 하루에 해당하는 일정 조회 API
    @GetMapping("/days/{day}")
    public List<ScheduleDayDto> readDayPlans(@PathVariable String day) {
        Long userId = 1L;
        return scheduleService.readDaySchedules(userId, day);
    }

    // 기간에 해당하는 일정 조회 API
    @GetMapping("")
    public List<ScheduleDaysDto> readPeriodPlans(@RequestParam String startDate,
                                                @RequestParam String endDate) {
        Long userId = 1L;
        return scheduleService.readPeriodPlans(userId, startDate, endDate);
    }

    // 개인 일정 수락 및 거절
    @PatchMapping("/{scheduleId}")
    public Boolean updateUserSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleDecideDto scheduleDecideDto) {
        return scheduleService.updateSchedule(scheduleId, scheduleDecideDto);
    }
}
