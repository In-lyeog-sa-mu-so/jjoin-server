package org.dongguk.jjoin.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.dto.request.NoticeRequestDto;
import org.dongguk.jjoin.dto.response.NoticeDto;
import org.dongguk.jjoin.service.ManagerService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/club/{clubId}/notice/{noticeId}")
    public NoticeDto readNotice(@PathVariable Long clubId, @PathVariable Long noticeId){
        return managerService.readNotice(clubId, noticeId);
    }

    @PutMapping("/club/{clubId}/notice/{noticeId}")
    public void updateNotice(@PathVariable Long clubId, @PathVariable Long noticeId, @RequestBody NoticeRequestDto noticeRequestDto){
        managerService.updateNotice(clubId, noticeId, noticeRequestDto);
    }
}
