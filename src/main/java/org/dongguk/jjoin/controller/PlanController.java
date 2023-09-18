package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @GetMapping("/calendar/{planId}")
    public ResponseDto<PlanDto> readPlan(@PathVariable Long planId) {
        return new ResponseDto<PlanDto>(planServcie.readPlan(planId));
    }

    @GetMapping("/calendar")
    public ResponseDto<List<PlanDto>> readPlans(@RequestParam("start") Timestamp start, @RequestParam("end") Timestamp end) {
        return new ResponseDto<List<PlanDto>>(planService.readPlans(start, end));
    }
}
