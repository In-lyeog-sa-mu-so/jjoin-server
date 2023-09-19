package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.dto.response.ScheduleDayDto;
import org.dongguk.jjoin.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/calendar/{targetDate}")
    public List<ScheduleDayDto> readDayPlans(@PathVariable String targetDate) {
        Long userId = 1L;
        return scheduleService.readDaySchedules(userId, targetDate);
    }

//    @GetMapping("/calendar")
//    public ResponseDto<List<PlanDayDto>> readWeekPlans(@RequestParam("startDate") Timestamp startDate, @RequestParam("endDate") Timestamp endDate) {
//        return new ResponseDto<List<PlanDayDto>>(planService.readWeekPlans(startDate, endDate));
//    }
}
