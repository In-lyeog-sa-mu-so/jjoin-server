package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Tag;
import org.dongguk.jjoin.repository.ClubMemberRepository;
import org.dongguk.jjoin.repository.ClubRepository;
import org.dongguk.jjoin.repository.ClubTagRepository;
import org.dongguk.jjoin.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SearchService {
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final TagRepository tagRepository;
    private final ClubTagRepository clubTagRepository;

    // 동아리 검색창 (태그 검색, 키워드 검색, 태그 + 키워드 검색, 검색 옵션 X 결과 화면 제공)
    public void searchClubs(String keyword, List<String> tags, Integer page, Integer size){
        List<Club> clubList;

        // 태그 검색
        if (!tags.isEmpty()) {
            List<Tag> tagList = tagRepository.findByNames(tags);
            if (!keyword.isEmpty()) { // 태그 + 키워드 검색
                clubList = clubRepository.findClubsByTagsAndKeyword(tagList, keyword);
            } else {
                clubList = clubRepository.findClubsByTags(tagList);
            }
        } else if (!keyword.isEmpty()) { // 키워드 검색
            clubList = clubRepository.findClubsByNameContainingOrIntroductionContaining(keyword, keyword);
        } else { // 검색 옵션 X
            clubList = clubRepository.findAll();
        }
    }
}
