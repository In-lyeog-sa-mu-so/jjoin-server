package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.*;
import org.dongguk.jjoin.domain.type.ImageType;
import org.dongguk.jjoin.domain.type.RankType;
import org.dongguk.jjoin.dto.page.ClubEnrollmentPageDto;
import org.dongguk.jjoin.dto.page.EnrollmentPageDto;
import org.dongguk.jjoin.dto.page.PageInfo;
import org.dongguk.jjoin.dto.request.ClubEnrollmentRequestDto;
import org.dongguk.jjoin.dto.request.EnrollmentUpdateDto;
import org.dongguk.jjoin.dto.response.ClubEnrollmentDto;
import org.dongguk.jjoin.dto.response.ClubEnrollmentResponseDto;
import org.dongguk.jjoin.dto.response.EnrollmentDto;
import org.dongguk.jjoin.repository.*;
import org.dongguk.jjoin.util.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {
    private final ClubRepository clubRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final ClubTagRepository clubTagRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final FileUtil fileUtil;

    // 모든 개설 신청서 조회
    public EnrollmentPageDto readEnrollments(Long page, Long size) {
        PageRequest pageable = PageRequest.of(page.intValue(), size.intValue(), Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Enrollment> enrollments = enrollmentRepository.findAll(pageable);
        List<EnrollmentDto> enrollmentDtos = new ArrayList<>();

        for (Enrollment enrollment : enrollments.getContent()) {
            Club club = enrollment.getClub();
            List<String> tags = club.getTags().stream()
                    .map(clubTag -> clubTag.getTag().getName()).collect(Collectors.toList());

            enrollmentDtos.add(EnrollmentDto.builder()
                    .id(enrollment.getId())
                    .clubName(club.getName())
                    .dependent(club.getDependent().toString())
                    .tags(tags)
                    .createdDate(enrollment.getCreatedDate())
                    .build());
        }

        return EnrollmentPageDto.builder()
                .data(enrollmentDtos)
                .pageInfo(PageInfo.builder()
                        .page(page.intValue())
                        .size(size.intValue())
                        .totalElements(enrollments.getTotalElements())
                        .totalPages(enrollments.getTotalPages())
                        .build())
                .build();
    }

    // 동아리 개설 신청 승인
    public Boolean updateEnrollments(EnrollmentUpdateDto enrollmentUpdateDto) {
        List<Long> ids = enrollmentUpdateDto.getIds();

        for (Long id : ids) {
            Club club = enrollmentRepository.findById(id).get().getClub();
            club.enrollClub();
            clubMemberRepository.save(ClubMember.builder()
                    .user(club.getLeader())
                    .club(club)
                    .rankType(RankType.LEADER)
                    .build());
        }
        enrollmentRepository.deleteByIds(ids);

        return Boolean.TRUE;
    }

    // 동아리 개설 신청 거부
    public Boolean deleteEnrollmentList(EnrollmentUpdateDto enrollmentUpdateDto) {
        List<Long> ids = enrollmentUpdateDto.getIds();

        for (Long id : ids) {
            Club club = enrollmentRepository.findById(id).get().getClub();
            enrollmentRepository.deleteById(id);
        }
        return Boolean.TRUE;
    }

    // 동아리 개설 신청서 목록 반환
    public ClubEnrollmentPageDto readClubEnrollments(Long userId, Integer page, Integer size) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        PageRequest pageable = PageRequest.of(page.intValue(), size.intValue(), Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Enrollment> enrollments = enrollmentRepository.findByUser(user, pageable);
        List<ClubEnrollmentDto> clubEnrollmentDtos = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            Club club = enrollment.getClub();
            clubEnrollmentDtos.add(ClubEnrollmentDto.builder()
                    .id(enrollment.getId())
                    .name(club.getName())
                    .dependent(club.getDependent().toString())
                    .tags(club.getTags().stream().map(clubTag -> clubTag.getTag().getName())
                            .collect(Collectors.toList()))
                    .createdDate(enrollment.getCreatedDate())
                    .build());
        }

        return ClubEnrollmentPageDto.builder()
                .data(clubEnrollmentDtos)
                .pageInfo(PageInfo.builder()
                        .page(page.intValue())
                        .size(size.intValue())
                        .totalElements(enrollments.getTotalElements())
                        .totalPages(enrollments.getTotalPages())
                        .build())
                .build();
    }

    // 동아리 개설 신청
    public Boolean createClubEnrollment(Long userId, ClubEnrollmentRequestDto data, MultipartFile clubImageFile, MultipartFile backgroundImageFile) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정

        String clubImageOriginName = clubImageFile.getOriginalFilename();
        String clubImageUuidName = fileUtil.storeFile(clubImageFile);
        Image clubImage = imageRepository.save(Image.builder()
                .user(user)
                .album(null)
                .notice(null)
                .originName(clubImageOriginName)
                .uuidName(clubImageUuidName)
                .type(ImageType.valueOf(fileUtil.getFileExtension(clubImageOriginName).toUpperCase()))
                .build());

        String backgroundImageOriginName = backgroundImageFile.getOriginalFilename();
        String backgroundImageUuidName = fileUtil.storeFile(backgroundImageFile);
        Image backgroundImage = imageRepository.save(Image.builder()
                .user(user)
                .album(null)
                .notice(null)
                .originName(backgroundImageOriginName)
                .uuidName(backgroundImageUuidName)
                .type(ImageType.valueOf(fileUtil.getFileExtension(backgroundImageOriginName).toUpperCase()))
                .build());

        Club club = clubRepository.save(Club.builder()
                .name(data.getName())
                .introduction(data.getIntroduction())
                .leader(user)
                .dependent(data.getDependent())
                .clubImage(clubImage)
                .backgroundImage(backgroundImage)
                .build());

        List<Tag> tags = tagRepository.findByNames(data.getTags());
        for (Tag tag : tags) {
            clubTagRepository.save(ClubTag.builder()
                    .club(club)
                    .tag(tag)
                    .build());
        }
        enrollmentRepository.save(Enrollment.builder()
                .club(club)
                .build());

        return Boolean.TRUE;
    }

    // 동아리 개설 신청서 상세 반환
    public ClubEnrollmentResponseDto readClubEnrollment(Long enrollmentId) {
        Enrollment enrollments = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new RuntimeException("NO enrollment")); // 예외처리 수정 예정
        Club club = enrollments.getClub();
        Image clubImage = club.getClubImage();
        Image backgroundImage = club.getBackgroundImage();

        return ClubEnrollmentResponseDto.builder()
                .name(club.getName())
                .introduction(club.getIntroduction())
                .dependent(club.getDependent().getDescription())
                .clubImageUuid(clubImage.getUuidName())
                .backgroundImageUuid(backgroundImage.getUuidName())
                .tags(club.getTags().stream().map(
                                clubTag -> clubTag.getTag().getName())
                        .collect(Collectors.toList()))
                .build();
    }
}
