package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.dto.response.SearchClubDto;
import org.dongguk.jjoin.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;
    @GetMapping
    public List<SearchClubDto> searchClubs(@RequestParam String keyword, @RequestParam List<String> tags, @RequestParam Integer page, @RequestParam Integer size){
        return searchService.searchClubs(keyword, tags, page, size);
    }
}