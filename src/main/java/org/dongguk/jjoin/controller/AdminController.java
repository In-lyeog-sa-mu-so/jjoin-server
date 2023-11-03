package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.dto.request.ClubDeletionUpdateDto;
import org.dongguk.jjoin.dto.request.EnrollmentUpdateDto;
import org.dongguk.jjoin.dto.response.ClubDeletionDto;
import org.dongguk.jjoin.dto.response.EnrollmentDto;
import org.dongguk.jjoin.service.ClubDeletionService;
import org.dongguk.jjoin.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final EnrollmentService enrollmentService;
    private final ClubDeletionService clubDeletionService;

    @GetMapping("/enrollment")
    public List<EnrollmentDto> readEnrollmentList() {
        return enrollmentService.readEnrollmentList();
    }

    @PutMapping("/enrollment")
    public Boolean updateEnrollmentList(@RequestBody EnrollmentUpdateDto enrollmentUpdateDto) {
        return enrollmentService.updateEnrollmentList(enrollmentUpdateDto);
    }

    @DeleteMapping("/enrollment")
    public Boolean deleteEnrollmentList(@RequestBody EnrollmentUpdateDto enrollmentUpdateDto) {
        return enrollmentService.deleteEnrollmentList(enrollmentUpdateDto);
    }

    @GetMapping("/deletion")
    public List<ClubDeletionDto> readClubDeletionList() {
        return clubDeletionService.readClubDeletionList();
    }

    @PutMapping("/deletion")
    public Boolean updateClubDeletion(@RequestBody ClubDeletionUpdateDto clubDeletionUpdateDto) {
        return clubDeletionService.updateClubDeltionList(clubDeletionUpdateDto);
    }
}
