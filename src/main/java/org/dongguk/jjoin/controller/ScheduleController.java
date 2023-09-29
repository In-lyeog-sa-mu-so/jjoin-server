package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
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

    @GetMapping("/days/{day}")
    public List<ScheduleDayDto> readDayPlans(@PathVariable String day) {
        Long userId = 1L;
        return scheduleService.readDaySchedules(userId, day, true);
    }

    @GetMapping("/weeks/{week}")
    public List<ScheduleDaysDto> readWeekPlans(@PathVariable String week) {
        Long userId = 1L;
        return scheduleService.readWeekSchedules(userId, week);
    }

    @GetMapping("/months/{month}")
    public List<ScheduleDaysDto> readMonthPlans(@PathVariable String month) {
        Long userId = 1L;
        return scheduleService.readMonthSchedules(userId, month);
    }

    @PatchMapping("/{scheduleId}")
    public Boolean updateUserSchedule(@PathVariable Long scheduleId, @RequestParam Boolean acceptCheck) {
        Long userId = 1L;
        return scheduleService.updateSchedule(scheduleId, acceptCheck);
    }
}
