package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.dto.request.ApplicationAnswerDto;
import org.dongguk.jjoin.dto.response.*;
import org.dongguk.jjoin.service.AlbumService;
import org.dongguk.jjoin.service.ClubService;
import org.dongguk.jjoin.service.ScheduleService;
import org.springframework.web.bind.annotation.*;
import org.dongguk.jjoin.dto.request.UserTagDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {
    private final ClubService clubService;
    private final ScheduleService scheduleService;
    private final AlbumService albumService;

    // 동아리 상세 조회 API
    @GetMapping("/{clubId}")
    public ClubDetailDto readClubDetail(@PathVariable Long clubId) {
        return clubService.readClubDetail(clubId);
    }

    // 동아리 게시글(공지, 홍보) 목록을 보여주는 API
    @GetMapping("/{clubId}/notices")
    public List<NoticeListDtoByApp> readNotices(@PathVariable Long clubId,
                                                @RequestParam("page") Long page,
                                                @RequestParam("size") Long size) {
        return clubService.readNotices(clubId, page, size);
    }

    // 동아리 게시글 상세정보를 보여주는 API
    @GetMapping("/{clubId}/notices/{noticeId}")
    public NoticeDto readNotice(@PathVariable Long clubId, @PathVariable Long noticeId) {
        return clubService.readNotice(clubId, noticeId);
    }

    // 특정 동아리의 일정 목록 조회 API
    @GetMapping("/{clubId}/schedules")
    public List<ClubScheduleDto> readClubSchedules(@PathVariable Long clubId,
                                                   @RequestParam("page") Long page,
                                                   @RequestParam("size") Long size) {
        Long userId = 1L;
        return scheduleService.readClubSchedules(userId, clubId, page, size);
    }

    @GetMapping("/{clubId}/schedules/{scheduleId}")
    public ClubScheduleDetailDto readClubScheduleDetail(@PathVariable Long clubId, @PathVariable Long scheduleId) {
        return scheduleService.readClubScheduleDetail(clubId, scheduleId);
    }

    @GetMapping("/{clubId}/albums")
    public List<ClubAlbumDto> readClubAlbumList(@PathVariable Long clubId, @RequestParam("page") Long page) {
        return albumService.readClubAlbumList(clubId, page);
    }

    @GetMapping("/{clubId}/albums/{albumId}")
    public ClubAlbumDetailDto readClubAlbumDetail(@PathVariable Long clubId, @PathVariable Long albumId) {
        return albumService.readClubAlbumDetail(clubId, albumId);
    }

    @GetMapping("/recommends")
    public List<ClubRecommendDto> readClubRecommend(@RequestBody List<UserTagDto> userTagDtoList) {
        Long userId = 2L;
        return clubService.readClubRecommend(userId, userTagDtoList);
    }

    // 동아리 가입신청서 양식 가져오기
    @GetMapping("/{clubId}/applications")
    public ApplicationFormDto readClubApplication(@PathVariable Long clubId) {
        return clubService.readClubApplication(clubId);
    }

    // 동아리 가입신청서 제출
    @PostMapping("/{clubId}/applications")
    public void submitClubApplication(@PathVariable Long clubId, @RequestBody List<ApplicationAnswerDto> applicationAnswerDtos) {
        clubService.submitClubApplication(clubId, applicationAnswerDtos);
    }
}
