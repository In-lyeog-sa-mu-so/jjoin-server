package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Notice;
import org.dongguk.jjoin.dto.page.NoticeAppPageDto;
import org.dongguk.jjoin.dto.page.PageInfo;
import org.dongguk.jjoin.dto.request.ApplicationAnswerDto;
import org.dongguk.jjoin.dto.response.*;
import org.dongguk.jjoin.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final TagRepository tagRepository;
    private final NoticeRepository noticeRepository;

    // 동아리 게시글(공지, 홍보) 목록 반환
    public NoticeAppPageDto readNotices(Long clubId, Integer page, Integer size) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("no match clubId"));
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedDate").descending());
        Page<Notice> notices = noticeRepository.findAllByClubAndNotDeleted(club, pageable);
        List<NoticeListDtoByApp> noticeListDtoByApps = new ArrayList<>();

        for (Notice notice : notices) {
            noticeListDtoByApps.add(NoticeListDtoByApp.builder()
                    .id(notice.getId())
                    .title(notice.getTitle())
                    .content(notice.getContent())
                    .updatedDate(notice.getUpdatedDate()).build());
        }
        return NoticeAppPageDto.builder()
                .data(noticeListDtoByApps)
                .pageInfo(PageInfo.builder()
                        .page(page)
                        .size(size)
                        .totalElements(notices.getTotalElements())
                        .totalPages(notices.getTotalPages())
                        .build())
                .build();
    }

    // 동아리 게시글 상세정보를 보여주는 API
    public NoticeDto readNotice(Long clubId, Long noticeId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("no match clubId"));
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new RuntimeException("No match Notice"));

        return NoticeDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdDate(notice.getCreatedDate())
                .updatedDate(notice.getUpdatedDate()).build();
    }

    // 추천 동아리 목록 반환
    public List<ClubRecommendDto> readClubRecommend(Long userId, UserTagDto userTagDtos) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        List<Tag> userTags = tagRepository.findByNames(userTagDtos.getTags());
        List<Club> userClubs = user.getClubMembers().stream().map(cm -> cm.getClub()).collect(Collectors.toList());
        List<ClubTag> clubTags = clubTagRepository.findByTagAndClubNotInUserClubs(userTags, userClubs);
        List<ClubRecommendDto> clubRecommendDtos = new ArrayList<>();

        for (ClubTag clubTag : clubTags) {
            Club club = clubTag.getClub();
            clubRecommendDtos.add(ClubRecommendDto.builder()
                    .id(club.getId())
                    .name(club.getName())
                    .introduction(club.getIntroduction())
                    .numberOfMembers(clubMemberRepository.countAllByClub(club))
                    .dependent(club.getDependent().getDescription())
                    .profileImageUuid(club.getClubImage().getUuidName())
                    .tags(club.getTags().stream().map(ct -> ct.getTag().getName()).collect(Collectors.toList()))
                    .build());
        }
        return clubRecommendDtos.subList(0, 5);
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
                .dependent(club.getDependent().getDescription())
                .profileImageUuid(club.getClubImage().getUuidName())
                .backgroundImageUuid(club.getBackgroundImage().getUuidName())
                .createdDate(club.getCreatedDate())
                .startDate(period[0])
                .endDate(period[1])
                .isFinished(recruitedPeriod.map(rp -> rp.getIsFinished()).orElse(null))
                .build();
    }

    public List<UserClubDto> readUserClubs(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Club> clubList = clubMemberRepository.findUserClubsByUser(user);
        List<UserClubDto> userClubDtoList = new ArrayList<>();

        for (Club club : clubList) {
            userClubDtoList.add(UserClubDto.builder()
                    .id(club.getId())
                    .clubImageUuid(club.getClubImage().getUuidName())
                    .name(club.getName())
                    .build());
        }
        return userClubDtoList;
    }

    // 동아리 가입신청서 양식 가져오기
    public ApplicationFormDto readClubApplication(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("No match Club"));
        Recruited_period recruitedPeriod = recruitedPeriodRepository.findByClub(club).orElseThrow(() -> new RuntimeException("No match Recruited Period"));
        Timestamp period[] = recruitedPeriod.getPeriod();
        List<Application_question> applicationQuestions = questionRepository.findAllByClubId(clubId);
        if (applicationQuestions == null || applicationQuestions.isEmpty()) {
            throw new RuntimeException("A Club has No application");
        }

        List<ApplicationQuestionResponseDto> applicationQuestionResponseDtos = new ArrayList<>();
        for (Application_question aQ : applicationQuestions) {
            applicationQuestionResponseDtos.add(ApplicationQuestionResponseDto.builder()
                    .id(aQ.getId())
                    .content(aQ.getContent())
                    .build());
        }
        return ApplicationFormDto.builder()
                .clubName(club.getName())
                .startDate(period[0])
                .endDate(period[1])
                .applicationQuestionResponseDtos(applicationQuestionResponseDtos)
                .build();
    }

    // 동아리 가입신청서 제출
    public void submitClubApplication(Long clubId, List<ApplicationAnswerDto> applicationAnswerDtos) {
        clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("No match Club"));
        User user = userRepository.findById(8L).get(); //User.getUser() 이 부분 수정 필요!!! 현재는 무조건 8번 id를 가진 유저가 신청한 걸로 설정함.
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
