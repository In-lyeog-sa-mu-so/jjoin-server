package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Notice;
import org.dongguk.jjoin.dto.request.ApplicationAnswerDto;
import org.dongguk.jjoin.dto.response.*;
import org.dongguk.jjoin.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.dongguk.jjoin.domain.*;
import org.dongguk.jjoin.dto.request.UserTagDto;

import java.sql.Timestamp;
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
    private final QuestionRepository questionRepository;
    private final ApplicationRepository applicationRepository;
    private final AnswerRepository answerRepository;

    // 동아리 게시글(공지, 홍보) 목록 반환
    public List<NoticeListDtoByApp> readNotices(Long clubId, Long page, Long size) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("no match clubId"));
        List<Notice> notices = club.getNotices();
        notices.removeIf(notice -> notice.isDeleted());
        notices.sort(Comparator.comparing(Notice::getUpdatedDate).reversed());
        int startIdx = page.intValue() * size.intValue();
        List<Notice> showNotices = notices.subList(startIdx, Math.min(startIdx + size.intValue(), notices.size()));
        List<NoticeListDtoByApp> noticeListDtoByApps = new ArrayList<>();

        for (Notice notice : showNotices) {
            noticeListDtoByApps.add(NoticeListDtoByApp.builder()
                    .id(notice.getId())
                    .title(notice.getTitle())
                    .content(notice.getContent())
                    .updatedDate(notice.getUpdatedDate()).build());
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
        List<ClubRecommendDto> clubRecommendDtoList = new ArrayList<>(clubRecommendDtoMap.values());
        clubRecommendDtoList.sort(Comparator.comparing(ClubRecommendDto::getUserNumber).reversed());

        return clubRecommendDtoList;
    }

    // 동아리 상세 조회 정보를 반환
    public ClubDetailDto readClubDetail(Long clubId) {
        Club club = clubRepository.findById(clubId).get();
        Optional<Recruited_period> recruitedPeriod = recruitedPeriodRepository.findByClub(club);
        // 모집 기간을 한번도 설정하지 않은 동아리라면 null값으로 설정
        Timestamp period[] = recruitedPeriod.map(rp -> rp.getPeriod()).orElse(new Timestamp[]{null, null});
        List<String> tags = club.getTags().stream()
                .map(clubTag -> clubTag.getTag().getName())
                .collect(Collectors.toList());

        return ClubDetailDto.builder()
                .id(club.getId())
                .name(club.getName())
                .tags(tags)
                .introduction(club.getIntroduction())
                .leaderName(club.getLeader().getName())
                .numberOfMembers(clubMemberRepository.countAllByClub(club))
                .dependent(club.getDependent().toString())
                .profileImageUuid(club.getClubImage().getUuidName())
                .backgroundImageUuid(club.getBackgroundImage().getUuidName())
                .createdDate(club.getCreatedDate())
                .startDate(period[0])
                .endDate(period[1])
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

    // 동아리 가입신청서 양식 가져오기
    public ApplicationFormDto readClubApplication(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("No match Club"));
        Optional<Recruited_period> recruitedPeriod = recruitedPeriodRepository.findByClub(club);
        // 모집 기간을 한번도 설정하지 않은 동아리라면 null값으로 설정
        Timestamp period[] = recruitedPeriod.map(rp -> rp.getPeriod()).orElse(new Timestamp[]{null, null});
        List<Application_question> applicationQuestions = questionRepository.findAllByClubId(clubId);
        if (applicationQuestions == null || applicationQuestions.isEmpty()) {
            throw new RuntimeException("A Club has No application");
        }

        List<ApplicationQuestionDto> applicationQuestionDtos = new ArrayList<>();
        for (Application_question aQ : applicationQuestions) {
            applicationQuestionDtos.add(ApplicationQuestionDto.builder()
                    .id(aQ.getId())
                    .content(aQ.getContent())
                    .build());
        }
        return ApplicationFormDto.builder()
                .clubName(club.getName())
                .startDate(period[0])
                .endDate(period[1])
                .applicationQuestionDtos(applicationQuestionDtos)
                .build();
    }

    // 동아리 가입신청서 제출
    public void submitClubApplication(Long clubId, List<ApplicationAnswerDto> applicationAnswerDtos) {
        clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("No match Club"));
        User user = new User(); //User.getUser() 이 부분 수정 필요!!! 현재는 작동 안됨.
        List<Application_answer> applicationAnswers = new ArrayList<>();

        for (ApplicationAnswerDto applicationAnswerDto : applicationAnswerDtos) {
            Application_question applicationQuestion = questionRepository.findById(applicationAnswerDto.getQuestionId()).orElseThrow(() -> new RuntimeException("No match Question"));
            applicationAnswers.add(Application_answer.builder()
                    .applicationQuestion(applicationQuestion)
                    .user(user)
                    .content(applicationAnswerDto.getAnswerContent())
                    .build());
        }
        answerRepository.saveAll(applicationAnswers);
        applicationRepository.save(ClubApplication.builder()
                .user(user)
                .club(clubRepository.findById(clubId).get())
                .build());
    }
}
