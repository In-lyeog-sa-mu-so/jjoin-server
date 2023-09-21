package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.dto.request.NoticeRequestDto;
import org.dongguk.jjoin.dto.response.NoticeDto;
import org.dongguk.jjoin.dto.response.NoticeListDto;
import org.dongguk.jjoin.service.ManagerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("/club/{clubId}/notice")
    public void createNotice(@PathVariable Long clubId, @RequestBody NoticeRequestDto noticeRequestDto){
        Long userId = 1L;   //  로그인 구현시 @GetUser 같은 어노테이션으로 대체해야함
        managerService.createNotice(userId, clubId, noticeRequestDto);
    }
    @GetMapping("/club/{clubId}/notice")
    public List<NoticeListDto> showNoticeList(@PathVariable Long clubId, @RequestParam("page") Integer page, @RequestParam("size") Integer size){
        return managerService.showNoticeList(clubId, page, size);
    }

    @GetMapping("/club/{clubId}/notice/{noticeId}")
    public NoticeDto readNotice(@PathVariable Long clubId, @PathVariable Long noticeId){
        return managerService.readNotice(clubId, noticeId);
    }

    @PutMapping("/club/{clubId}/notice/{noticeId}")
    public void updateNotice(@PathVariable Long clubId, @PathVariable Long noticeId, @RequestBody NoticeRequestDto noticeRequestDto){
        managerService.updateNotice(clubId, noticeId, noticeRequestDto);
    }

    @DeleteMapping("/club/{clubId}/notice/{noticeId}")
    public Boolean deleteNotice(@PathVariable Long clubId, @PathVariable Long noticeId){
        managerService.deleteNotice(clubId, noticeId);
        return Boolean.TRUE;
    }
}
