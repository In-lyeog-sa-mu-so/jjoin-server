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

    // 관리자가 동아리 개설 신청서를 읽어오는 API
    @GetMapping("/enrollment")
    public List<EnrollmentDto> readEnrollments(@RequestParam Long page, @RequestParam Long size) {
        return enrollmentService.readEnrollments(page, size);
    }

    // 관리자가 동아리 개설을 승인하는 API
    @PutMapping("/enrollment")
    public Boolean updateEnrollments(@RequestBody EnrollmentUpdateDto enrollmentUpdateDto) {
        return enrollmentService.updateEnrollments(enrollmentUpdateDto);
    }

    // 관리자가 동아리 개설을 거부하는 API
    @DeleteMapping("/enrollment")
    public Boolean deleteEnrollments(@RequestBody EnrollmentUpdateDto enrollmentUpdateDto) {
        return enrollmentService.deleteEnrollmentList(enrollmentUpdateDto);
    }

    // 관리자가 동아리 삭제 신청 목록을 조회하는 API
    @GetMapping("/deletion")
    public List<ClubDeletionDto> readClubDeletions(@RequestParam Long page, @RequestParam Long size) {
        return clubDeletionService.readClubDeletions(page, size);
    }

    // 관리자가 동아리 삭제 신청을 승인하는 API
    @PutMapping("/deletion")
    public Boolean updateClubDeletion(@RequestBody ClubDeletionUpdateDto clubDeletionUpdateDto) {
        return clubDeletionService.updateClubDeletions(clubDeletionUpdateDto);
    }
}
