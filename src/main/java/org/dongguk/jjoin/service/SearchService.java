package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Recruited_period;
import org.dongguk.jjoin.domain.Tag;
import org.dongguk.jjoin.dto.response.SearchClubDto;
import org.dongguk.jjoin.repository.*;
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
    public List<SearchClubDto> searchClubs(String keyword, List<String> tags, Integer page, Integer size) {
        List<Club> clubs = new ArrayList<>();
        // 태그 검색
        if (!tags.isEmpty()) {
            List<Tag> tagList = tagRepository.findByNames(tags);
            if (!keyword.isEmpty()) { // 태그 + 키워드 검색
                clubs = clubRepository.findClubsByTagsAndKeyword(tagList, keyword);
            } else {
                clubs = clubRepository.findClubsByTags(tagList);
            }
        } else if (!keyword.isEmpty()) { // 키워드 검색
            clubs = clubRepository.findClubsByNameContainingOrIntroductionContaining(keyword, keyword);
        } else { // 검색 옵션 X
            clubs = clubRepository.findAll();
        }

        // 페이지네이션 결과로 보여줄 클럽 개수만큼만 정보 담아서 반환
        int startIdx = page * size;
        List<Club> neededClubs = clubs.subList(startIdx, Math.min(startIdx + size, clubs.size()));
        List<SearchClubDto> searchClubDtos = new ArrayList<>();
        for (Club club : neededClubs) {
            Optional<Recruited_period> recruitedPeriod = recruitedPeriodRepository.findByClub(club);
            // 모집 기간을 한번도 설정하지 않은 동아리라면 null값으로 설정
            Timestamp period[] = recruitedPeriod.map(rp -> rp.getPeriod()).orElse(new Timestamp[]{null, null});
            searchClubDtos.add(SearchClubDto.builder()
                    .clubId(club.getId())
                    .clubName(club.getName())
                    .introduction(club.getIntroduction())
                    .userNumber(clubMemberRepository.countAllByClub(club))
                    .dependent(club.getDependent().toString())
                    .profileImageUuid(club.getClubImage().getUuidName())
                    .startDate(period[0])
                    .endDate(period[1])
                    .build());
        }
        return searchClubDtos;
    }
}
