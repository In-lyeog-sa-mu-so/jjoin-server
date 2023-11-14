package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.dto.request.ClubDeletionUpdateDto;
import org.dongguk.jjoin.dto.request.EnrollmentUpdateDto;
import org.dongguk.jjoin.dto.response.ClubDeletionDto;
import org.dongguk.jjoin.dto.response.ClubEnrollmentDto;
import org.dongguk.jjoin.dto.response.ClubEnrollmentResponseDto;
import org.dongguk.jjoin.dto.response.EnrollmentDto;
import org.dongguk.jjoin.service.ClubDeletionService;
import org.dongguk.jjoin.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final EnrollmentService enrollmentService;
    private final ClubDeletionService clubDeletionService;

    // 관리자가 동아리 개설 신청서를 읽어오는 API
    @GetMapping("/enrollment")
    public Map<String, Object> readEnrollments(@RequestParam Long page, @RequestParam Long size) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", enrollmentService.readEnrollments(page, size));
        return result;
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ClubEnrollmentResponseDto readEnrollment(@PathVariable Long enrollmentId){
        return enrollmentService.readClubEnrollment(enrollmentId);
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
    public Map<String, Object> readClubDeletions(@RequestParam Long page, @RequestParam Long size) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", clubDeletionService.readClubDeletions(page, size));
        return result;
    }

    // 관리자가 동아리 삭제 신청을 승인하는 API
    @PutMapping("/deletion")
    public Boolean updateClubDeletion(@RequestBody ClubDeletionUpdateDto clubDeletionUpdateDto) {
        return clubDeletionService.updateClubDeletions(clubDeletionUpdateDto);
    }
}
