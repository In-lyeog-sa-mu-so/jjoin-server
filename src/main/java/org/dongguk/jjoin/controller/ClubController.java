package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.dto.response.*;
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

    @GetMapping("/{clubId}")
    public ClubDetailDto showClub(@PathVariable Long clubId) {
        return clubService.showClub(clubId);
    }

    // 동아리 게시글(공지, 홍보) 목록을 보여주는 API
    @GetMapping("/{clubId}/notices")
    public List<NoticeListDtoByApp> showNoticeList(@PathVariable Long clubId, @RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return clubService.showNoticeList(clubId, page, size);
    }

    // 동아리 게시글 상세정보를 보여주는 API
    @GetMapping("/{clubId}/notices/{noticeId}")
    public NoticeDto readNotice(@PathVariable Long clubId, @PathVariable Long noticeId){
        return clubService.readNotice(clubId, noticeId);
    }

    @GetMapping("/{clubId}/schedules")
    public List<ClubScheduleDto> readClubScheduleList(@PathVariable Long clubId, @RequestParam("page") Long page) {
        Long userId = 1L;
        return scheduleService.readClubSchedules(userId, clubId, page);
    }

    @GetMapping("/recommends")
    public List<ClubRecommendDto> readClubRecommend(@RequestBody List<UserTagDto> userTagDtoList) {
        Long userId = 2L;
        return clubService.readClubRecommend(userId, userTagDtoList);
    }
}
