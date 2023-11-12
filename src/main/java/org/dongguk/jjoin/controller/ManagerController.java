package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.dto.request.ClubEnrollmentRequestDto;
import org.dongguk.jjoin.dto.request.ApplicationQuestionDto;
import org.dongguk.jjoin.dto.request.NoticeRequestDto;
import org.dongguk.jjoin.dto.request.PlanRequestDto;
import org.dongguk.jjoin.dto.request.PlanUpdateDto;
import org.dongguk.jjoin.dto.response.*;
import org.dongguk.jjoin.dto.ClubMemberDtoByWeb;
import org.dongguk.jjoin.service.EnrollmentService;
import org.dongguk.jjoin.service.ManagerService;
import org.dongguk.jjoin.service.PlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;
    private final PlanService planService;
    private final EnrollmentService enrollmentService;

    // 관리자가 관리하는 동아리 조회
    @GetMapping("/club")
    public List<ManagingClubDto> showJoinedClubs(){
        Long userId = 1L;
        return managerService.showJoinedClubs(userId);
    }

    @PostMapping("/club/{clubId}/notice")
    public void createNotice(@PathVariable Long clubId, @RequestBody NoticeRequestDto noticeRequestDto) {
        Long userId = 1L;   //  로그인 구현시 @GetUser 같은 어노테이션으로 대체해야함
        managerService.createNotice(userId, clubId, noticeRequestDto);
    }

    @GetMapping("/club/{clubId}/notice")
    public List<NoticeListDto> showNoticeList(@PathVariable Long clubId, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return managerService.showNoticeList(clubId, page, size);
    }

    @GetMapping("/club/{clubId}/notice/{noticeId}")
    public NoticeDto readNotice(@PathVariable Long clubId, @PathVariable Long noticeId) {
        return managerService.readNotice(clubId, noticeId);
    }

    @PutMapping("/club/{clubId}/notice/{noticeId}")
    public void updateNotice(@PathVariable Long clubId, @PathVariable Long noticeId, @RequestBody NoticeRequestDto noticeRequestDto) {
        managerService.updateNotice(clubId, noticeId, noticeRequestDto);
    }

    @DeleteMapping("/club/{clubId}/notice/{noticeId}")
    public Boolean deleteNotice(@PathVariable Long clubId, @PathVariable Long noticeId) {
        managerService.deleteNotice(clubId, noticeId);
        return Boolean.TRUE;
    }

    @GetMapping("/club/{clubId}/plan")
    public List<PlanDto> readPlanList(@PathVariable Long clubId) {
        return planService.readPlanList(clubId);
    }

    @PostMapping("/club/{clubId}/plan")
    public Boolean createPlan(@PathVariable Long clubId, @RequestBody PlanRequestDto planRequestDto) {
        return planService.createPlan(clubId, planRequestDto);
    }

    @PutMapping("/club/{clubId}/plan/{planId}")
    public Boolean updatePlan(@RequestBody PlanUpdateDto planUpdateDto) {
        return planService.updatePlan(planUpdateDto);
    }

    @DeleteMapping("/club/{clubId}/plan/{planId}")
    public Boolean deletePlan(@PathVariable Long planId) {
        return planService.deletePlan(planId);
    }

    // 동아리 멤버 목록 조회
    @GetMapping("/club/{clubId}/member")
    public List<ClubMemberDtoByWeb> readClubMembers(@PathVariable Long clubId, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return managerService.readClubMembers(clubId, page, size);
    }

    // 동아리 멤버 권한 수정
    @PatchMapping("/club/{clubId}/member/{userId}/rank/{rankType}")
    public void modifyMemberRole(@PathVariable Long clubId, @PathVariable Long userId, @PathVariable String rankType) {
        managerService.modifyMemberRole(clubId, userId, rankType);
    }

    // 동아리 멤버 퇴출
    @DeleteMapping("/club/{clubId}/member/{userId}")
    public void deleteMember(@PathVariable Long clubId, @PathVariable List<Long> userId) {
        managerService.deleteMember(clubId, userId);
    }

    // 동아리 기존 메인페이지 조회
    @GetMapping("/club/{clubId}/information")
    public ClubMainPageDtoByWeb readClubMainPage(@PathVariable Long clubId) {
        return managerService.readClubMainPage(clubId);
    }

    // 동아리 메인페이지 수정
    @PutMapping("/club/{clubId}/information")
    public void modifyClubMainPage(@PathVariable Long clubId, @RequestBody ClubMainPageDtoByWeb clubMainPageDtoByWeb) {
        managerService.modifyClubMainPage(clubId, clubMainPageDtoByWeb);
    }

    @GetMapping("/enrollment")
    public List<ClubEnrollmentDto> readClubEnrollmentList() {
        Long userId = 1L;
        return enrollmentService.readClubEnrollmentList(userId);
    }

    @PostMapping("/enrollment")
    public Boolean createClubEnrollment(@RequestBody ClubEnrollmentRequestDto clubEnrollmentRequestDto) {
        Long userId = 1L;
        return enrollmentService.createClubEnrollment(userId, clubEnrollmentRequestDto);
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ClubEnrollmentResponseDto readClubEnrollment(@PathVariable Long enrollmentId) {
        return enrollmentService.readClubEnrollment(enrollmentId);
    }
  
    // 동아리 가입 신청서 질문 생성
    @PostMapping("/club/{clubId}/application")
    public void makeApplicationQuestion(@PathVariable Long clubId, @RequestBody List<ApplicationQuestionDto> applicationQuestionDtos){
        managerService.makeApplicationQuestion(clubId, applicationQuestionDtos);
    }

    // 동아리 가입 신청 목록
    @GetMapping("/club/{clubId}/application")
    public List<ApplicationDto> readApplicationList(@PathVariable Long clubId, @RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return managerService.readApplicationList(clubId, page, size);
    }

    // 동아리 가입 신청 상세보기
    @GetMapping("/club/{clubId}/application/{applicationId}")
    public ApplicationDetailDto readApplication(@PathVariable Long clubId, @PathVariable Long applicationId){
        return managerService.readApplication(clubId, applicationId);
    }

    // 동아리 가입 신청 수락
    @PatchMapping("/club/{clubId}/application/{applicationId}")
    public void acceptApplication(@PathVariable Long clubId, @PathVariable Long applicationId){
        managerService.acceptApplication(clubId, applicationId);
    }

    // 동아리 가입 신청 거절
    @DeleteMapping("/club/{clubId}/application/{applicationId}")
    public void refuseApplication(@PathVariable Long clubId, @PathVariable Long applicationId) {
        managerService.refuseApplication(clubId, applicationId);
    }
}
