package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.*;
import org.dongguk.jjoin.domain.type.RankType;
import org.dongguk.jjoin.dto.request.ApplicationQuestionDto;
import org.dongguk.jjoin.dto.request.NoticeRequestDto;
import org.dongguk.jjoin.dto.response.*;
import org.dongguk.jjoin.dto.ClubMemberDtoByWeb;
import org.dongguk.jjoin.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ManagerService {
    private final ClubRepository clubRepository;
    private final NoticeRepository noticeRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final RecruitedPeriodRepository recruitedPeriodRepository;
    private final ImageRepository imageRepository;
    private final QuestionRepository questionRepository;
    private final ApplicationRepository applicationRepository;
    private final AnswerRepository answerRepository;

    public List<NoticeListDto> showNoticeList(Long clubId, Integer page, Integer size){
        Club club = clubRepository.findById(clubId).orElseThrow(()-> new RuntimeException("no match clubId"));
        List<Notice> notices = Optional.ofNullable(club.getNotices()).orElseThrow(()-> new RuntimeException("Notice Not found!"));
        notices.removeIf(notice -> notice.isDeleted());
        notices.sort(Comparator.comparing(Notice::getUpdatedDate).reversed());

        int startIdx = page * size;
        List<Notice> showNotices = notices.subList(startIdx, Math.min(startIdx + size, notices.size()));
        List<NoticeListDto> noticeListDtos = new ArrayList<>();
        for (Notice n : showNotices){
            noticeListDtos.add(NoticeListDto.builder()
                    .id(n.getId())
                    .title(n.getTitle())
                    .updatedDate(n.getUpdatedDate()).build());
        }
        return noticeListDtos;
    }

    public void createNotice(Long userId, Long clubId, NoticeRequestDto noticeRequestDto){
        // 유저 유무, 클럽 존재유무 확인
        //User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("createNotice club없음!"));
        Club club = clubRepository.findById(clubId).orElseThrow(()-> new RuntimeException("createNotice club없음!"));

        noticeRepository.save(Notice.builder()
                        .title(noticeRequestDto.getTitle())
                        .isPrivate(noticeRequestDto.getIsPrivate())
                        .content(noticeRequestDto.getContent())
                        .club(club).build());
    }

    public Notice searchNotice(Long clubId, Long noticeId){
        Club club = clubRepository.findById(clubId).orElseThrow(()-> new RuntimeException("no match clubId"));
        Notice notice = club.getNotices().stream()
                .filter(n -> n.getId().equals(noticeId)).findAny()
                .orElseThrow(() -> new RuntimeException("No match noticeId"));
        return notice;
    }

    public NoticeDto readNotice(Long clubId, Long noticeId){
        Notice notice = searchNotice(clubId, noticeId);

        return NoticeDto.builder()
                        .id(notice.getId())
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .createdDate(notice.getCreatedDate())
                        .updatedDate(notice.getUpdatedDate()).build();
    }

    public void updateNotice(Long clubId, Long noticeId, NoticeRequestDto noticeRequestDto){
        Notice notice = searchNotice(clubId, noticeId);

        notice.updateNotice(noticeRequestDto);
    }

    public Boolean deleteNotice(Long clubId, Long noticeId){
        Notice notice = searchNotice(clubId, noticeId);
        notice.deleteNotice();
        return Boolean.TRUE;
    }

    // 동아리 멤버 목록 조회
    public List<ClubMemberDtoByWeb> readClubMembers(Long clubId, Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ClubMember> clubMembers = clubMemberRepository.findByClubId(clubId, pageRequest);
        List<ClubMemberDtoByWeb> clubMemberDtoByWebs = new ArrayList<>();
        for (ClubMember cm : clubMembers){
            User user = cm.getUser();
            clubMemberDtoByWebs.add(ClubMemberDtoByWeb.builder()
                            .userId(user.getId())
                            .userName(user.getName())
                            .major(user.getMajor())
                            .studentId(user.getStudentId())
                            .registerDate(cm.getRegisterDate())
                            .position(cm.getRankType())
                    .build());
        }
        return clubMemberDtoByWebs;
    }

    // 동아리 멤버 권한 수정
    public void modifyMemberRole(Long clubId, Long userId, String rankType){
        //        User user = GetUser();
//        if (clubMemberRepository.findByUser(user).getRankType().equals(RankType.LEADER)){
//            throw new RuntimeException("권한 없어용");
//        }
        ClubMember clubMember = clubMemberRepository.findClubMemberByClubIdAndUserId(clubId, userId).orElseThrow(()-> new RuntimeException("No clubMember"));
        clubMember.modifyRank(RankType.valueOf(rankType));
    }

    // 동아리 멤버 퇴출
    public void deleteMember(Long clubId, List<Long> userIds){
//        User user = GetUser();
//        if (clubMemberRepository.findByUser(user).getRankType().equals(RankType.LEADER)){
//            throw new RuntimeException("권한 없어용");
//        }
        clubMemberRepository.deleteAllByClubIdAndUserId(clubId, userIds);
    }

    // 동아리 기존 메인페이지 조회
    public ClubMainPageDtoByWeb readClubMainPage(Long clubId){
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        Recruited_period recruitedPeriod = recruitedPeriodRepository.findByClub(club).orElseThrow(()->new RuntimeException("No match Club"));
        return ClubMainPageDtoByWeb.builder()
                .clubImage(club.getClubImage().getUuidName())
                .backgroundImage(club.getBackgroundImage().getUuidName())
                .introduction(club.getIntroduction())
                .isFinished(recruitedPeriod.getIsFinished())
                .startDate(recruitedPeriod.getStartDate())
                .endDate(recruitedPeriod.getEndDate())
                .build();
    }

    // 수정 결과로 받아온 사진들이 이미 존재한 사진인지 확인. 변경된 새로운 사진일 경우 새롭게 저장
    public Image checkisExistImage(Long clubId, String originName){
        return imageRepository.findByOriginName(originName).orElseGet(() -> imageRepository.save(
                Image.builder()
//                        .user(GetUser())
//                        .album()
//                        .notice()
//                        .originName(originName)
//                        .uuidName()
//                        .type()
                        .build()
        ));
    }

    // 동아리 메인페이지 수정
    public void modifyClubMainPage(Long clubId, ClubMainPageDtoByWeb clubMainPageDtoByWeb){
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        Recruited_period recruitedPeriod = recruitedPeriodRepository.findByClub(club).orElseThrow(() -> new RuntimeException("No Recruit_periods"));
//        Image clubImage = checkisExistImage(clubId, clubMainPageDtoByWeb.getClubImage());
//        Image backgroundImage = checkisExistImage(clubId, clubMainPageDtoByWeb.getBackgroundImage());
        // club 사진들을 업데이트 로직 추가 필요.
        club.setIntroduction(clubMainPageDtoByWeb.getIntroduction());
        recruitedPeriod.setIsFinished(clubMainPageDtoByWeb.getIsFinished());
        recruitedPeriod.setStartDate(clubMainPageDtoByWeb.getStartDate());
        recruitedPeriod.setEndDate(clubMainPageDtoByWeb.getEndDate());
    }

    // 동아리 가입 신청서 질문 생성
    public void makeApplicationQuestion(Long clubId, List<ApplicationQuestionDto> applicationQuestionDtos){
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        List<Application_question> applicationQuestions = new ArrayList<>();
        for(ApplicationQuestionDto applicationQuestionDto : applicationQuestionDtos){
            applicationQuestions.add(
                    Application_question.builder()
                            .club(club)
                            .content(applicationQuestionDto.getContent())
                            .build()
            );
            questionRepository.saveAll(applicationQuestions);
        }
    }

    // 가입 신청 목록, 상세보기에 쓰이는 ClubApplication 엔티티를 입력으로 받아 applicationDtos 만드는 함수
    ApplicationDto makeApplicationDto(ClubApplication clubApplication){
        User user = clubApplication.getUser();
        return ApplicationDto.builder()
                .name(user.getName())
                .studentId(user.getStudentId())
                .major(user.getMajor().toString())
                .email(user.getEmail())
                .requestDate(clubApplication.getRequestDate())
                .build();
    }

    // 동아리 가입 신청 목록
    public List<ApplicationDto> readApplicationList(Long clubId, Integer page, Integer size){
        clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ClubApplication> clubApplications = applicationRepository.findApplicationList(clubId, pageRequest);
        List<ApplicationDto> applicationDtos = new ArrayList<>();

        for (ClubApplication clubApplication: clubApplications){
            applicationDtos.add(makeApplicationDto(clubApplication));
        }
        return applicationDtos;
    }

    // 동아리 가입 신청 상세보기
    public ApplicationDetailDto readApplication(Long clubId, Long applicationId){
        clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        ClubApplication clubApplication = applicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("NO Application"));
        User user = clubApplication.getUser();

        List<Application_question> applicationQuestions = questionRepository.findAllByClubId(clubId);
        List<ApplicationQAset> applicationQAsetList = new ArrayList<>();

        for (int i = 0; i < applicationQuestions.size(); i++){
            Application_question applicationQuestion = applicationQuestions.get(i);
            Application_answer applicationAnswer = answerRepository.findAllByApplicationQuestionId(applicationQuestion.getId());
            applicationQAsetList.add(ApplicationQAset.builder()
                            .question(applicationQuestion.getContent())
                            .answer(applicationAnswer.getContent())
                    .build());
        }

        return ApplicationDetailDto.builder()
                .applicationDto(makeApplicationDto(clubApplication))
                .applicationQAsets(applicationQAsetList)
                .build();
    }

    // 동아리 가입 신청 수락
    public void acceptApplication(Long clubId, Long applicationId){
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        ClubApplication clubApplication = applicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("NO Application"));
        User user = clubApplication.getUser();

        clubMemberRepository.save(ClubMember.builder()
                        .user(user)
                        .club(clubApplication.getClub())
                        .rankType(RankType.MEMBER)
                .build());
        answerRepository.deleteAllByUserAndQuestion(user.getId(), questionRepository.findAllByClubId(clubId));
        applicationRepository.deleteById(applicationId);
    }
}