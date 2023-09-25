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

    @GetMapping("/{targetDate}")
    public List<ScheduleDayDto> readDayPlans(@PathVariable String targetDate) {
        Long userId = 1L;
        return scheduleService.readDaySchedules(userId, targetDate);
    }

//    @GetMapping("")
//    public List<ScheduleDaysDto> readWeekPlans(@RequestParam("week") String weekNum) {
//        Long userId = 1L;
//        return scheduleService.readWeekSchedules(userId, weekNum);
//    }

    @GetMapping("")
    public List<ScheduleDaysDto> readMonthPlans(@RequestParam("month") String monthNum) {
        Long userId = 1L;
        return scheduleService.readMonthSchedules(userId, monthNum);
    }
}
