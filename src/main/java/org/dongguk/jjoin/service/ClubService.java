package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Notice;
import org.dongguk.jjoin.dto.response.*;
import org.dongguk.jjoin.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.dongguk.jjoin.domain.*;
import org.dongguk.jjoin.dto.request.UserTagDto;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClubService {
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubTagRepository clubTagRepository;
    private final UserRepository userRepository;
    private final RecruitedPeriodRepository recruitedPeriodRepository;

    // 동아리 게시글(공지, 홍보) 목록을 보여주는 API
    public List<NoticeListDtoByApp> showNoticeList(Long clubId, Integer page, Integer size){
        Club club = clubRepository.findById(clubId).orElseThrow(()-> new RuntimeException("no match clubId"));
        List<Notice> notices = Optional.ofNullable(club.getNotices()).orElseThrow(()-> new RuntimeException("Notice Not found!"));
        notices.removeIf(notice -> notice.isDeleted());
        notices.sort(Comparator.comparing(Notice::getUpdatedDate).reversed());

        int startIdx = page * size;
        List<Notice> showNotices = notices.subList(startIdx, Math.min(startIdx + size, notices.size()));
        List<NoticeListDtoByApp> noticeListDtoByApps = new ArrayList<>();
        for (Notice n : showNotices){
            noticeListDtoByApps.add(NoticeListDtoByApp.builder()
                            .id(n.getId())
                            .title(n.getTitle())
                            .content(n.getContent())
                            .updatedDate(n.getUpdatedDate()).build());
        }
        return noticeListDtoByApps;
    }

    // 동아리 게시글 상세정보를 보여주는 API
    public NoticeDto readNotice(Long clubId, Long noticeId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("no match clubId"));
        Notice notice = club.getNotices().stream()
                .filter(n -> n.getId().equals(noticeId)).findAny()
                .orElseThrow(() -> new RuntimeException("No match noticeId"));

        return NoticeDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdDate(notice.getCreatedDate())
                .updatedDate(notice.getUpdatedDate()).build();
    }
  
    public List<ClubRecommendDto> readClubRecommend(Long userId, List<UserTagDto> userTagDtoList) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        // 사용자가 가입한 동아리 제외 반환을 위해 조회
        List<Club> userClubs = clubMemberRepository.findUserClubsByUser(user);
        List<ClubTag> clubRecommendList = new ArrayList<>();
        userTagDtoList.forEach(clubTag ->
                clubRecommendList.addAll(clubTagRepository.findByTagIdNotInUserClub(clubTag.getId(), userClubs)));

        Map<Long, ClubRecommendDto> clubRecommendDtoMap = new HashMap<>();
        for (ClubTag clubTag : clubRecommendList) {
            Club club = clubTag.getClub();
            List<String> clubTagList = new ArrayList<>();
            clubTagRepository.findByClub(club).forEach(clubTag1 ->
                    clubTagList.add(clubTag1.getTag().getName()));

            clubRecommendDtoMap.put(club.getId(), ClubRecommendDto.builder()
                    .clubId(club.getId())
                    .clubName(club.getName())
                    .introduction(club.getIntroduction())
                    .profileImageUuid(club.getClubImage().getUuidName())
                            .userNumber(clubMemberRepository.countAllByClub(club))
                            .dependent(club.getDependent().toString())
                            .tags(clubTagList)
                    .build());
        }
        List<ClubRecommendDto> clubRecommendDtoList= new ArrayList<>(clubRecommendDtoMap.values());
        clubRecommendDtoList.sort(Comparator.comparing(ClubRecommendDto::getUserNumber).reversed());

        return clubRecommendDtoList;
    }

    public ClubDetailDto showClub(Long clubId) {
        Club club = clubRepository.findById(clubId).get();
        Recruited_period recruitedPeriod = recruitedPeriodRepository.findByClub(club);
        List<String> tags = new ArrayList<>();
        club.getTags().forEach(clubTag -> tags.add(clubTag.getTag().getName()));

        return ClubDetailDto.builder()
                .clubId(club.getId())
                .clubName(club.getName())
                .tag(tags)
                .introduction(club.getIntroduction())
                .leaderName(club.getLeader().getName())
                .userNumber(clubMemberRepository.countAllByClub(club))
                .dependent(club.getDependent().toString())
                .backgroundImageUuid(club.getBackgroundImage().getUuidName())
                .profileImageUuid(club.getClubImage().getUuidName())
                .createdDate(club.getCreatedDate())
                .startDate(recruitedPeriod.getStartDate())
                .endDate(recruitedPeriod.getEndDate())
                .build();
    }

    public List<UserClubDto> readUserClubList(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Club> clubList = clubMemberRepository.findUserClubsByUser(user);
        List<UserClubDto> userClubDtoList = new ArrayList<>();

        for (Club club : clubList) {
            userClubDtoList.add(UserClubDto.builder()
                            .clubId(club.getId())
                            .clubImage(club.getClubImage().getUuidName())
                            .clubName(club.getName())
                    .build());
        }

        return userClubDtoList;
    }
}
