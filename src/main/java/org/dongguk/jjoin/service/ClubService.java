package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.*;
import org.dongguk.jjoin.dto.request.UserTagDto;
import org.dongguk.jjoin.dto.response.ClubRecommendDto;
import org.dongguk.jjoin.repository.ClubMemberRepository;
import org.dongguk.jjoin.repository.ClubRepository;
import org.dongguk.jjoin.repository.ClubTagRepository;
import org.dongguk.jjoin.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClubService {
    UserRepository userRepository;
    ClubRepository clubRepository;
    ClubMemberRepository clubMemberRepository;
    ClubTagRepository clubTagRepository;
    public List<ClubRecommendDto> readClubRecommend(Long userId, List<UserTagDto> userTagDtoList) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        // 사용자가 가입한 동아리 제외 반환을 위해 조회
        List<ClubMember> userClubs = clubMemberRepository.findAllByUser(user);
        Set<ClubTag> clubRecommendSet = new HashSet<>();
        // 아직 사용자가 가입한 동아리 제외 반환 구현x
        userTagDtoList.forEach(clubTag ->
                clubRecommendSet.addAll(clubTagRepository.findByTagId(clubTag.getId())));

        List<ClubRecommendDto> clubRecommendDtoList = new ArrayList<>();
        for (ClubTag clubTag : clubRecommendSet) {
            Club club = clubTag.getClub();
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

        return clubRecommendDtoList;
    }
}
