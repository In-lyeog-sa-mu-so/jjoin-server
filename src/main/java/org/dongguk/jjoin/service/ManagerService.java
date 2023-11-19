package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.*;
import org.dongguk.jjoin.domain.type.ImageType;
import org.dongguk.jjoin.domain.type.RankType;
import org.dongguk.jjoin.dto.page.ApplicationPageDto;
import org.dongguk.jjoin.dto.page.ClubMemberPageDto;
import org.dongguk.jjoin.dto.page.NoticeWebPageDto;
import org.dongguk.jjoin.dto.page.PageInfo;
import org.dongguk.jjoin.dto.request.ApplicationQuestionDto;
import org.dongguk.jjoin.dto.request.QuestionDeleteDto;
import org.dongguk.jjoin.dto.request.QuestionModifyDto;
import org.dongguk.jjoin.dto.request.NoticeRequestDto;
import org.dongguk.jjoin.dto.response.*;
import org.dongguk.jjoin.dto.response.ClubMemberDto;
import org.dongguk.jjoin.dto.response.ClubMainPageUpdateDto;
import org.dongguk.jjoin.dto.response.NoticeDto;
import org.dongguk.jjoin.dto.response.NoticeListDto;
import org.dongguk.jjoin.repository.*;
import org.dongguk.jjoin.util.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    private final FileUtil fileUtil;
    private final QuestionRepository questionRepository;
    private final ApplicationRepository applicationRepository;
    private final AnswerRepository answerRepository;

    // 관리자가 관리하는 동아리 조회
    public List<ManagingClubDto> showJoinedClubs(Long userId){
        List<Club> clubs = clubMemberRepository.findClubMemberByUserIdAndRankType(userId, RankType.MEMBER);
        List<ManagingClubDto> managingClubDtos = new ArrayList<>();
        for (Club club: clubs){
            managingClubDtos.add(ManagingClubDto.builder()
                    .id(club.getId())
                    .name(club.getName())
                    .introduction(club.getIntroduction())
                    .leaderName(club.getLeader().getName())
                    .numberOfMembers(clubMemberRepository.countAllByClub(club))
                    .dependent(club.getDependent().getDescription())
                    .profileImageUuid(club.getClubImage().getUuidName())
                    .build());
        }
        return managingClubDtos;
    }

    // 동아리 게시글 생성
    public void createNotice(Long userId, Long clubId, NoticeRequestDto noticeRequestDto) {
        // 유저 유무, 클럽 존재유무 확인
        //User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("createNotice club없음!"));
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("createNotice club없음!"));

        noticeRepository.save(Notice.builder()
                .title(noticeRequestDto.getTitle())
                .isPrivate(noticeRequestDto.getIsPrivate())
                .content(noticeRequestDto.getContent())
                .club(club).build());
    }

    // 동아리 게시글 목록 조회
    public NoticeWebPageDto showNoticeList(Long clubId, Integer page, Integer size){
        Club club = clubRepository.findById(clubId).orElseThrow(()-> new RuntimeException("no match clubId"));
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Notice> notices = noticeRepository.findAllByClubAndNotDeleted(club, pageable);
        long totalElements = notices.getTotalElements();
        int totalPages = notices.getTotalPages();
        Long noticeNumber = totalElements - (page*size);

        List<NoticeListDto> noticeListDtos = new ArrayList<>();
        for (Notice n : notices.getContent()) {
            noticeListDtos.add(NoticeListDto.builder()
                    .id(n.getId())
                    .noticeNumber(noticeNumber--)
                    .title(n.getTitle())
                    .createdDate(n.getCreatedDate()).build());
        }
        return NoticeWebPageDto.builder()
                .data(noticeListDtos)
                .pageInfo(PageInfo.builder()
                        .page(page)
                        .size(size)
                        .totalElements(totalElements)
                        .totalPages(totalPages)
                        .build())
                .build();
    }

    public Notice searchNotice(Long clubId, Long noticeId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("no match clubId"));
        Notice notice = club.getNotices().stream()
                .filter(n -> n.getId().equals(noticeId)).findAny()
                .orElseThrow(() -> new RuntimeException("No match noticeId"));
        return notice;
    }

    public NoticeDto readNotice(Long clubId, Long noticeId) {
        Notice notice = searchNotice(clubId, noticeId);

        return NoticeDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdDate(notice.getCreatedDate())
                .updatedDate(notice.getUpdatedDate()).build();
    }

    // 동아리 게시글 수정
    public void updateNotice(Long clubId, Long noticeId, NoticeRequestDto noticeRequestDto) {
        Notice notice = searchNotice(clubId, noticeId);

        notice.updateNotice(noticeRequestDto);
    }

    public Boolean deleteNotice(Long clubId, Long noticeId) {
        Notice notice = searchNotice(clubId, noticeId);
        notice.deleteNotice();
        return Boolean.TRUE;
    }

    // 동아리 멤버 목록 조회
    public ClubMemberPageDto readClubMembers(Long clubId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ClubMember> clubMembers = clubMemberRepository.findByClubId(clubId, pageRequest);
        List<ClubMemberDto> clubMemberDtos = new ArrayList<>();
        for (ClubMember cm : clubMembers) {
            User user = cm.getUser();
            clubMemberDtos.add(ClubMemberDto.builder()
                    .userId(user.getId())
                    .userName(user.getName())
                    .major(user.getMajor())
                    .email(user.getEmail())
                    .studentId(user.getStudentId())
                    .registerDate(cm.getRegisterDate())
                    .position(cm.getRankType())
                    .build());
        }
        return ClubMemberPageDto.builder()
                .data(clubMemberDtos)
                .pageInfo(PageInfo.builder()
                        .page(page)
                        .size(size)
                        .totalElements(clubMembers.getTotalElements())
                        .totalPages(clubMembers.getTotalPages())
                        .build())
                .build();
    }

    // 동아리 멤버 권한 수정
    public void modifyMemberRole(Long clubId, Long userId, String rankType) {
        //        User user = GetUser();
//        if (clubMemberRepository.findByUser(user).getRankType().equals(RankType.LEADER)){
//            throw new RuntimeException("권한 없어용");
//        }
        ClubMember clubMember = clubMemberRepository.findClubMemberByClubIdAndUserId(clubId, userId).orElseThrow(() -> new RuntimeException("No clubMember"));
        clubMember.modifyRank(RankType.valueOf(rankType));
    }

    // 동아리 멤버 퇴출
    public void deleteMember(Long clubId, List<Long> userIds) {
//        User user = GetUser();
//        if (clubMemberRepository.findByUser(user).getRankType().equals(RankType.LEADER)){
//            throw new RuntimeException("권한 없어용");
//        }
        clubMemberRepository.deleteAllByClubIdAndUserId(clubId, userIds);
    }

    // 동아리 기존 메인페이지 조회
    public ClubMainPageDtoByWeb readClubMainPage(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        Optional<Recruited_period> recruitedPeriod = recruitedPeriodRepository.findByClub(club);
        // 모집 기간을 한번도 설정하지 않은 동아리라면 null값으로 설정
        Timestamp period[] = recruitedPeriod.map(rp -> rp.getPeriod()).orElse(new Timestamp[]{null, null});

        return ClubMainPageDtoByWeb.builder()
                .clubImageUuid(club.getClubImage().getUuidName())
                .backgroundImageUuid(club.getBackgroundImage().getUuidName())
                .introduction(club.getIntroduction())
                .startDate(period[0])
                .endDate(period[1])
                .isFinished(recruitedPeriod.map(rp -> rp.getIsFinished()).orElse(null))
                .build();
    }


    // 동아리 메인페이지 수정
    public void modifyClubMainPage(Long clubId, ClubMainPageUpdateDto data,
                                   MultipartFile clubImageFile, MultipartFile backgroundImageFile) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        Optional<Recruited_period> recruitedPeriod = recruitedPeriodRepository.findByClub(club);

        String clubImageOriginName = clubImageFile.getOriginalFilename();
        String clubImageUuidName = fileUtil.storeFile(clubImageFile);
        Image clubImage = imageRepository.save(Image.builder()
                .user(club.getLeader())
                .album(null)
                .notice(null)
                .originName(clubImageOriginName)
                .uuidName(clubImageUuidName)
                .type(ImageType.valueOf(fileUtil.getFileExtension(clubImageOriginName).toUpperCase())).build());

        String backgroundImageOriginName = backgroundImageFile.getOriginalFilename();
        String backgroundImageUuidName = fileUtil.storeFile(backgroundImageFile);
        Image backgroundImage = imageRepository.save(Image.builder()
                .user(club.getLeader())
                .album(null)
                .notice(null)
                .originName(backgroundImageOriginName)
                .uuidName(backgroundImageUuidName)
                .type(ImageType.valueOf(fileUtil.getFileExtension(backgroundImageOriginName).toUpperCase())).build());

        club.modifyClubMain(data.getIntroduction(), clubImage, backgroundImage);
        // 모집 기간 설정을 한번이라도 한다면 해당 데이터를 수정, 없다면 새로 생성
        if (recruitedPeriod.isPresent()) {
            recruitedPeriod.get().updatePeriod(data.getStartDate(), data.getEndDate());
        } else {
            recruitedPeriodRepository.save(Recruited_period.builder()
                            .club(club)
                            .startDate(data.getStartDate())
                            .endDate(data.getEndDate())
                    .build());
        }
    }

    // 동아리 가입 신청서 질문 생성
    public void makeApplicationQuestion(Long clubId, List<ApplicationQuestionDto> applicationQuestionDtos) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        List<Application_question> applicationQuestions = new ArrayList<>();
        for (ApplicationQuestionDto applicationQuestionDto : applicationQuestionDtos) {
            applicationQuestions.add(
                    Application_question.builder()
                            .club(club)
                            .content(applicationQuestionDto.getContent())
                            .build()
            );
            questionRepository.saveAll(applicationQuestions);
        }
    }

    // 동아리 가입 신청 질문 조회
    public List<ApplicationQuestionResponseDto> readApplicationQuestion(Long clubId) {
        clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        List<Application_question> applicationQuestions = questionRepository.findAllByClubId(clubId);
        List<ApplicationQuestionResponseDto> applicationQuestionDtos = new ArrayList<>();
        for (Application_question aQ : applicationQuestions) {
            applicationQuestionDtos.add(ApplicationQuestionResponseDto.builder()
                    .id(aQ.getId())
                    .content(aQ.getContent())
                    .build());
        };
        return applicationQuestionDtos;
    }

    // 동아리 가입 신청서 질문 수정
    public void modifyApplicationQuestion(Long clubId, List<QuestionModifyDto> questionModifyDtos) {
        clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        for (QuestionModifyDto questionModifyDto : questionModifyDtos){
            Application_question applicationQuestion = questionRepository.findByClubIdAndId(clubId, questionModifyDto.getId()).orElseThrow(() -> new RuntimeException("No application question"));
            applicationQuestion.modifyContent(questionModifyDto.getContent());
        }
    }

    // 동아리 가입 신청서 질문 삭제
    public void deleteApplicationQuestion(Long clubId, List<QuestionDeleteDto> questionDeleteDtos) {
        clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        List<Long> ids = new ArrayList<>();
        for (QuestionDeleteDto aq : questionDeleteDtos) {
            ids.add(aq.getId());
        }
        questionRepository.deleteAllByClubIdAndId(clubId, ids);
    }

    // 가입 신청 목록, 상세보기에 쓰이는 ClubApplication 엔티티를 입력으로 받아 applicationDtos 만드는 함수
    ApplicationDto makeApplicationDto(ClubApplication clubApplication) {
        User user = clubApplication.getUser();
        return ApplicationDto.builder()
                .id(clubApplication.getId())
                .name(user.getName())
                .studentId(user.getStudentId())
                .major(user.getMajor().toString())
                .email(user.getEmail())
                .requestDate(clubApplication.getRequestDate())
                .build();
    }

    // 동아리 가입 신청 목록
    public ApplicationPageDto readApplicationList(Long clubId, Integer page, Integer size) {
        clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ClubApplication> clubApplications = applicationRepository.findApplicationList(clubId, pageRequest);
        List<ApplicationDto> applicationDtos = new ArrayList<>();

        for (ClubApplication clubApplication : clubApplications) {
            applicationDtos.add(makeApplicationDto(clubApplication));
        }
        return ApplicationPageDto.builder()
                .data(applicationDtos)
                .pageInfo(PageInfo.builder()
                        .page(page)
                        .size(size)
                        .totalElements(clubApplications.getTotalElements())
                        .totalPages(clubApplications.getTotalPages())
                        .build())
                .build();
    }

    // 동아리 가입 신청 상세보기
    public ApplicationDetailDto readApplication(Long clubId, Long applicationId) {
        clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        ClubApplication clubApplication = applicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("NO Application"));
        User user = clubApplication.getUser();
        List<Application_question> applicationQuestions = questionRepository.findAllByClubId(clubId);
        List<ApplicationQAset> applicationQAsetList = new ArrayList<>();

        for (int i = 0; i < applicationQuestions.size(); i++) {
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

    // 동아리 가입 신청 수락,거절 시 제출한 신청서와 답변을 DB에서 삭제하는 함수
    public void removeApplication(Long userId, Long clubId, Long applicationId) {
        answerRepository.deleteAllByUserAndQuestion(userId, questionRepository.findAllByClubId(clubId));
        applicationRepository.deleteById(applicationId);
    }

    // 동아리 가입 신청 수락
    public void acceptApplication(Long clubId, Long applicationId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        ClubApplication clubApplication = applicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("NO Application"));
        User user = clubApplication.getUser();

        clubMemberRepository.save(ClubMember.builder()
                .user(user)
                .club(clubApplication.getClub())
                .rankType(RankType.MEMBER)
                .build());
        removeApplication(user.getId(), clubId, applicationId);
    }

    // 동아리 가입 신청 거절
    public void refuseApplication(Long clubId, Long applicationId) {
        clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("NO Club"));
        ClubApplication clubApplication = applicationRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("NO Application"));
        removeApplication(clubApplication.getUser().getId(), clubId, applicationId);
    }
}