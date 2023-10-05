package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.dto.response.ClubDetailDto;
import org.dongguk.jjoin.dto.response.NoticeDto;
import org.dongguk.jjoin.dto.response.NoticeListDtoByApp;
import org.dongguk.jjoin.service.ClubService;
import org.springframework.web.bind.annotation.*;
import org.dongguk.jjoin.dto.request.UserTagDto;
import org.dongguk.jjoin.dto.response.ClubRecommendDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {
    private final ClubService clubService;

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

    @GetMapping("/recommends")
    public List<ClubRecommendDto> readClubRecommend(@RequestBody List<UserTagDto> userTagDtoList) {
        Long userId = 2L;
        return clubService.readClubRecommend(userId, userTagDtoList);
    }
}
