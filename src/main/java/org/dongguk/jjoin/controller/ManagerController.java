package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.dto.page.ApplicationPageDto;
import org.dongguk.jjoin.dto.page.ClubEnrollmentPageDto;
import org.dongguk.jjoin.dto.page.ClubMemberPageDto;
import org.dongguk.jjoin.dto.page.NoticeWebPageDto;
import org.dongguk.jjoin.dto.request.*;
import org.dongguk.jjoin.dto.response.*;
import org.dongguk.jjoin.service.EnrollmentService;
import org.dongguk.jjoin.service.ManagerService;
import org.dongguk.jjoin.service.PlanService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> showJoinedClubs(){
        Long userId = 1L;
        Map<String, Object> result = new HashMap<>();
        result.put("data", managerService.showJoinedClubs(userId));
        return result;
    }

    // 동아리 게시글 생성
    @PostMapping("/club/{clubId}/notice")
    public void createNotice(@PathVariable Long clubId, @RequestBody NoticeRequestDto noticeRequestDto) {
        Long userId = 1L;   //  로그인 구현시 @GetUser 같은 어노테이션으로 대체해야함
        managerService.createNotice(userId, clubId, noticeRequestDto);
    }

    // 동아리 게시글 목록 조회
    @GetMapping("/club/{clubId}/notice")
    public NoticeWebPageDto showNoticeList(@PathVariable Long clubId, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return managerService.showNoticeList(clubId, page, size);
    }

    // 동아리 게시글 상세보기
    @GetMapping("/club/{clubId}/notice/{noticeId}")
    public NoticeDto readNotice(@PathVariable Long clubId, @PathVariable Long noticeId) {
        return managerService.readNotice(clubId, noticeId);
    }

    // 동아리 게시글 수정
    @PutMapping("/club/{clubId}/notice/{noticeId}")
    public void updateNotice(@PathVariable Long clubId, @PathVariable Long noticeId, @RequestBody NoticeRequestDto noticeRequestDto) {
        managerService.updateNotice(clubId, noticeId, noticeRequestDto);
    }

    @DeleteMapping("/club/{clubId}/notice/{noticeId}")
    public Boolean deleteNotice(@PathVariable Long clubId, @PathVariable Long noticeId) {
        managerService.deleteNotice(clubId, noticeId);
        return Boolean.TRUE;
    }

    // 특정 동아리의 일정 목록 조회 API
    @GetMapping("/club/{clubId}/plan")
    public Map<String, Object> readPlans(@PathVariable Long clubId) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", planService.readPlans(clubId));
        return result;
    }

    // 특정 동아리의 일정 등록 API
    @PostMapping("/club/{clubId}/plan")
    public Boolean createPlan(@PathVariable Long clubId, @RequestBody PlanRequestDto planRequestDto) {
        return planService.createPlan(clubId, planRequestDto);
    }

    // 특정 동아리의 일정 상세 조회 API
    @GetMapping("/club/{clubId}/plan/{planId}")
    public PlanDto readPlanDetail(@PathVariable Long clubId, @PathVariable Long planId) {
        return planService.readPlanDetail(clubId, planId);
    }

    // 특정 동아리의 일정 정보 수정 API
    @PutMapping("/club/{clubId}/plan/{planId}")
    public Boolean updatePlan(@PathVariable Long clubId, @PathVariable Long planId, @RequestBody PlanUpdateDto planUpdateDto) {
        return planService.updatePlan(clubId, planId, planUpdateDto);
    }

    // 특정 동아리의 일정 삭제 API
    @DeleteMapping("/club/{clubId}/plan/{planId}")
    public Boolean deletePlan(@PathVariable Long clubId, @PathVariable Long planId) {
        return planService.deletePlan(clubId, planId);
    }

    // 동아리 멤버 목록 조회
    @GetMapping("/club/{clubId}/member")
    public ClubMemberPageDto readClubMembers(@PathVariable Long clubId, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
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
    public void modifyClubMainPage(@PathVariable Long clubId, @RequestPart ClubMainPageUpdateDto data,
                                   @RequestPart MultipartFile clubImageFile,
                                   @RequestPart MultipartFile backgroundImageFile) {
        managerService.modifyClubMainPage(clubId, data, clubImageFile, backgroundImageFile);
    }

    // 동아리 개설 신청서 목록 조회 API
    @GetMapping("/enrollment")
    public ClubEnrollmentPageDto readClubEnrollments(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Long userId = 1L;
        return enrollmentService.readClubEnrollments(userId, page, size);
    }

    // 동아리 개설 API
    @PostMapping("/enrollment")
    public Boolean createClubEnrollment(@RequestPart ClubEnrollmentRequestDto data,
                                        @RequestPart MultipartFile clubImageFile,
                                        @RequestPart MultipartFile backgroundImageFile) {
        Long userId = 1L;
        return enrollmentService.createClubEnrollment(userId, data, clubImageFile, backgroundImageFile);
    }

    // 동아리 개설 신청서 조회 API
    @GetMapping("/enrollment/{enrollmentId}")
    public ClubEnrollmentResponseDto readClubEnrollment(@PathVariable Long enrollmentId) {
        return enrollmentService.readClubEnrollment(enrollmentId);
    }
  
    // 동아리 가입 신청서 질문 생성
    @PostMapping("/club/{clubId}/question")
    public void makeApplicationQuestion(@PathVariable Long clubId, @RequestBody List<ApplicationQuestionDto> applicationQuestionDtos){
        managerService.makeApplicationQuestion(clubId, applicationQuestionDtos);
    }

    // 동아리 가입 신청서 질문 조회
    @GetMapping("/club/{clubId}/question")
    public Map<String, Object> readApplicationQuestion(@PathVariable Long clubId) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", managerService.readApplicationQuestion(clubId));
        return result;
    }

    // 동아리 가입 신청서 질문 수정
    @PatchMapping("/club/{clubId}/question")
    public void modifyApplicationQuestion(@PathVariable Long clubId, @RequestBody List<QuestionModifyDto> questionModifyDtos) {
        managerService.modifyApplicationQuestion(clubId, questionModifyDtos);
    }

    // 동알 가입 신청서 질문 삭제
    @DeleteMapping("/club/{clubId}/question")
    public void deleteApplicationQuestion(@PathVariable Long clubId, @RequestBody List<QuestionDeleteDto> questionDeleteDtos) {
        managerService.deleteApplicationQuestion(clubId, questionDeleteDtos);
    }

    // 동아리 가입 신청 목록
    @GetMapping("/club/{clubId}/application")
    public ApplicationPageDto readApplicationList(@PathVariable Long clubId, @RequestParam("page") Integer page, @RequestParam("size") Integer size){
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
