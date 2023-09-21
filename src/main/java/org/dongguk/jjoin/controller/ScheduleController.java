package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.dto.response.ScheduleDayDto;
import org.dongguk.jjoin.dto.response.ScheduleWeekDto;
import org.dongguk.jjoin.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/{targetDate}")
    public List<ScheduleDayDto> readDayPlans(@PathVariable String targetDate) {
        Long userId = 1L;
        return scheduleService.readDaySchedules(userId, targetDate);
    }

    @GetMapping("")
    public List<ScheduleWeekDto> readWeekPlans(@RequestParam("week") String weekNum) {
        Long userId = 1L;
        return scheduleService.readWeekSchedules(userId, weekNum);
    }
}
