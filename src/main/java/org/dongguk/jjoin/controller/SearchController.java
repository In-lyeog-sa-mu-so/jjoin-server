package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.dto.page.SearchClubPageDto;
import org.dongguk.jjoin.dto.page.TagPageDto;
import org.dongguk.jjoin.dto.response.SearchClubDto;
import org.dongguk.jjoin.dto.response.TagDto;
import org.dongguk.jjoin.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    // 동아리 검색 API
    @GetMapping
    public SearchClubPageDto searchClubs(@RequestParam String keyword, @RequestParam List<String> tags, @RequestParam Integer page, @RequestParam Integer size){
        return searchService.searchClubs(keyword, tags, page, size);
    }

    // 동아리 검색하기 위해 모든 태그 목록 조회
    @GetMapping("/tags")
    public TagPageDto readAllTags(@RequestParam Integer page, @RequestParam Integer size) {
        return searchService.readAllTags(page, size);
    }
}