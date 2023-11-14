package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.dto.request.ScheduleDecideDto;
import org.dongguk.jjoin.dto.response.ScheduleDayDto;
import org.dongguk.jjoin.dto.response.ScheduleDaysDto;
import org.dongguk.jjoin.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 하루에 해당하는 일정 조회 API
    @GetMapping("/days/{day}")
    public Map<String, Object> readDayPlans(@PathVariable String day) {
        Long userId = 1L;
        Map<String, Object> result = new HashMap<>();
        result.put("data", scheduleService.readDaySchedules(userId, day));
        return result;
    }

    // 기간에 해당하는 일정 조회 API
    @GetMapping("")
    public Map<String, Object> readPeriodPlans(@RequestParam String startDate,
                                                @RequestParam String endDate) {
        Long userId = 1L;
        Map<String, Object> result = new HashMap<>();
        result.put("data", scheduleService.readPeriodPlans(userId, startDate, endDate));
        return result;
    }

    // 개인 일정 수락 및 거절
    @PatchMapping("/{scheduleId}")
    public Boolean updateUserSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleDecideDto scheduleDecideDto) {
        return scheduleService.updateSchedule(scheduleId, scheduleDecideDto);
    }
}
