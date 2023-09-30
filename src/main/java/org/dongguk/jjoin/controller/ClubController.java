package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.dto.request.UserTagDto;
import org.dongguk.jjoin.dto.response.ClubRecommendDto;
import org.dongguk.jjoin.service.ClubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;

    @GetMapping("/recommends")
    public List<ClubRecommendDto> readClubRecommend(@RequestBody List<UserTagDto> userTagDtoList) {
        Long userId = 1L;
        return clubService.readClubRecommend(userId, userTagDtoList);
    }
}
