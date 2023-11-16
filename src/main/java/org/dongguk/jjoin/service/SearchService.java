package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Recruited_period;
import org.dongguk.jjoin.domain.Tag;
import org.dongguk.jjoin.dto.page.PageInfo;
import org.dongguk.jjoin.dto.page.SearchClubPageDto;
import org.dongguk.jjoin.dto.page.TagPageDto;
import org.dongguk.jjoin.dto.response.SearchClubDto;
import org.dongguk.jjoin.dto.response.TagDto;
import org.dongguk.jjoin.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SearchService {
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final TagRepository tagRepository;
    private final RecruitedPeriodRepository recruitedPeriodRepository;

    // 동아리 검색창 (태그 검색, 키워드 검색, 태그 + 키워드 검색, 검색 옵션 X 결과 화면 제공)
    public SearchClubPageDto searchClubs(String keyword, List<String> tags, Integer page, Integer size) {
        Page<Club> clubs;
        Pageable pageable = PageRequest.of(page, size);
        // 태그 검색
        if (!tags.isEmpty()) {
            List<Tag> tagList = tagRepository.findByNames(tags);
            if (!keyword.isEmpty()) { // 태그 + 키워드 검색
                clubs = clubRepository.findClubsByTagsAndKeyword(tagList, keyword, pageable);
            } else {
                clubs = clubRepository.findClubsByTags(tagList, pageable);
            }
        } else if (!keyword.isEmpty()) { // 키워드 검색
            clubs = clubRepository.findClubsByNameContainingOrIntroductionContainingAndIsDeletedIsFalseAndCreatedDateIsNotNull(keyword, keyword, pageable);
        } else { // 검색 옵션 X
            clubs = clubRepository.findAll(pageable);
        }

        List<SearchClubDto> searchClubDtos = new ArrayList<>();
        for (Club club : clubs) {
            Optional<Recruited_period> recruitedPeriod = recruitedPeriodRepository.findByClub(club);
            // 모집 기간을 한번도 설정하지 않은 동아리라면 null값으로 설정
            Timestamp period[] = recruitedPeriod.map(rp -> rp.getPeriod()).orElse(new Timestamp[]{null, null});
            searchClubDtos.add(SearchClubDto.builder()
                    .clubId(club.getId())
                    .clubName(club.getName())
                    .introduction(club.getIntroduction())
                    .userNumber(clubMemberRepository.countAllByClub(club))
                    .leaderName(club.getLeader().getName())
                    .dependent(club.getDependent().toString())
                    .profileImageUuid(club.getClubImage().getUuidName())
                    .startDate(period[0])
                    .endDate(period[1])
                    .isFinished(recruitedPeriod.map(rp -> rp.getIsFinished()).orElse(null))
                    .build());
        }

        return SearchClubPageDto.builder()
                .clubs(searchClubDtos)
                .pageInfo(PageInfo.builder()
                        .page(page)
                        .size(size)
                        .totalElements(clubs.getTotalElements())
                        .totalPages(clubs.getTotalPages())
                        .build())
                .build();
    }

    // 동아리 검색하기 위해 모든 태그 목록 조회
    public TagPageDto readAllTags(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<TagDto> tagDtos = new ArrayList<>();
        Page<Tag> tags = tagRepository.findAllSort(pageable);

        for (Tag tag : tags.getContent()) {
            tagDtos.add(TagDto.builder()
                    .id(tag.getId())
                    .name(tag.getName())
                    .build());
        }
        return TagPageDto.builder()
                .data(tagDtos)
                .pageInfo(PageInfo.builder()
                        .page(page)
                        .size(size)
                        .totalElements(tags.getTotalElements())
                        .totalPages(tags.getTotalPages())
                        .build())
                .build();
    }
}
