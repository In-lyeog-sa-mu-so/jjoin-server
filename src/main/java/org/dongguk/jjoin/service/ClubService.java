package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.*;
import org.dongguk.jjoin.dto.request.UserTagDto;
import org.dongguk.jjoin.dto.response.ClubRecommendDto;
import org.dongguk.jjoin.repository.ClubMemberRepository;
import org.dongguk.jjoin.repository.ClubTagRepository;
import org.dongguk.jjoin.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClubService {
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubTagRepository clubTagRepository;

    public List<ClubRecommendDto> readClubRecommend(Long userId, List<UserTagDto> userTagDtoList) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        // 사용자가 가입한 동아리 제외 반환을 위해 조회
        List<Club> userClubs = clubMemberRepository.findUserClubsByUser(user);
        List<ClubTag> clubRecommendList = new ArrayList<>();
        userTagDtoList.forEach(clubTag ->
                clubRecommendList.addAll(clubTagRepository.findByTagId(clubTag.getId())));

        List<ClubRecommendDto> clubRecommendDtoList = new ArrayList<>();
        for (ClubTag clubTag : clubRecommendList) {
            Club club = clubTag.getClub();
            if (userClubs.contains(club))
                continue;

            List<String> clubTagList = new ArrayList<>();
            clubTagRepository.findByClub(club).forEach(clubTag1 ->
                    clubTagList.add(clubTag1.getTag().getName()));

            clubRecommendDtoList.add(ClubRecommendDto.builder()
                    .clubId(club.getId())
                    .clubName(club.getName())
                    .introduction(club.getIntroduction())
                    .profileImageUuid(club.getClubImage().getUuidName())
                            .userNumber(clubMemberRepository.countAllByClub(club))
                            .dependent(club.getDependent().toString())
                            .tags(clubTagList)
                    .build());
        }
        clubRecommendDtoList.sort(Comparator.comparing(ClubRecommendDto::getUserNumber).reversed());

        return clubRecommendDtoList;
    }
}
