package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.ClubMember;
import org.dongguk.jjoin.domain.User;
import org.dongguk.jjoin.dto.response.ClubCardDto;
import org.dongguk.jjoin.repository.ClubMemberRepository;
import org.dongguk.jjoin.repository.ClubRepository;
import org.dongguk.jjoin.repository.NoticeRepository;
import org.dongguk.jjoin.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final NoticeRepository noticeRepository;

    public List<ClubCardDto> readUserClubs(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        List<ClubMember> clubList = clubMemberRepository.findAllByUser(user);

        List<ClubCardDto> clubCardDtoList = new ArrayList<>();
        for (ClubMember clubMember : clubList) {
            Club club = clubRepository.findById(clubMember.getClub().getId()).get();
            clubCardDtoList.add(ClubCardDto.builder()
                    .clubId(club.getId())
                    .clubName(club.getName())
                    .introduction(club.getIntroduction())
                    .leaderName(club.getLeader().getName())
                    .userNumber(clubMemberRepository.countAllByClub(club))
                    .dependent(club.getDependent().toString())
                    .profileImageUuid(club.getClubImage().getUuidName())
                    .newestNotice(noticeRepository.findByClubOrderByCreatedDateDesc(club).getTitle())
                    .build());
        }

        return clubCardDtoList;
    }
}
