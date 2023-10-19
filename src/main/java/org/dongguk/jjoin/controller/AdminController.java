package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.dto.response.EnrollmentDto;
import org.dongguk.jjoin.service.EnrollmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final EnrollmentService enrollmentService;

    @GetMapping("/enrollment")
    public List<EnrollmentDto> readEnrollmentList() {
        return enrollmentService.readEnrollmentList();
    }
}
