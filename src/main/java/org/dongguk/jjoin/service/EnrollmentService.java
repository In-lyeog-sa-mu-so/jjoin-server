package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.*;
import org.dongguk.jjoin.domain.type.ImageType;
import org.dongguk.jjoin.dto.request.ClubEnrollmentRequestDto;
import org.dongguk.jjoin.dto.request.EnrollmentUpdateDto;
import org.dongguk.jjoin.dto.response.ClubEnrollmentDto;
import org.dongguk.jjoin.dto.response.ClubEnrollmentResponseDto;
import org.dongguk.jjoin.dto.response.EnrollmentDto;
import org.dongguk.jjoin.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<EnrollmentDto> readEnrollmentList() {
        List<Enrollment> enrollmentList = enrollmentRepository.findAll();
        List<EnrollmentDto> enrollmentDtoList = new ArrayList<>();

        for (Enrollment enrollment : enrollmentList) {
            Club club = enrollment.getClub();
            List<ClubTag> clubTagList = club.getTags();
            List<String> tagList = new ArrayList<>();
            clubTagList.forEach(tags -> tagList.add(tags.getTag().getName()));
            enrollmentDtoList.add(EnrollmentDto.builder()
                            .id(enrollment.getId())
                            .clubName(club.getName())
                            .dependent(club.getDependent().toString())
                            .tags(tagList)
                            .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                            .createdDate(enrollment.getCreatedDate())
                    .build());
        }

        return enrollmentDtoList;
    }

    public Boolean updateEnrollmentList(EnrollmentUpdateDto enrollmentUpdateDto) {
        List<Long> idList = enrollmentUpdateDto.getId();

        for (Long id : idList) {
            Enrollment enrollment = enrollmentRepository.findById(id).get();
            enrollment.getClub().enrollClub();
            enrollmentRepository.deleteById(id);
        }

        return true;
    }

    public Boolean deleteEnrollmentList(EnrollmentUpdateDto enrollmentUpdateDto) {
        List<Long> idList = enrollmentUpdateDto.getId();

        for (Long id : idList) {
            enrollmentRepository.deleteById(id);
        }

        return true;
    }

    public List<ClubEnrollmentDto> readClubEnrollmentList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
        List<ClubEnrollmentDto> clubEnrollmentDtos = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            Club club = enrollment.getClub();
            clubEnrollmentDtos.add(ClubEnrollmentDto.builder()
                            .id(enrollment.getId())
                            .clubName(club.getName())
                            .dependent(club.getDependent().toString())
                            .tags(club.getTags().stream().map(clubTag -> clubTag.getTag().getName())
                                    .collect(Collectors.toList()))
                            .createdDate(enrollment.getCreatedDate())
                    .build());
        }

        return clubEnrollmentDtos;
    }

    public Boolean createClubEnrollment(Long userId, ClubEnrollmentRequestDto clubEnrollmentRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정

        // 이미지 처리 필요
        Image clubImage = imageRepository.save(Image.builder()
                .user(user)
                .album(null)
                .notice(null)
                .originName(clubEnrollmentRequestDto.getClubImageOriginName())
                .uuidName(clubEnrollmentRequestDto.getClubImageUuidName())
                .type(ImageType.JPG).build());
        // 이미지 처리 필요
        Image backgroundImage = imageRepository.save(Image.builder()
                .user(user)
                .album(null)
                .notice(null)
                .originName(clubEnrollmentRequestDto.getBackgroundImageOriginName())
                .uuidName(clubEnrollmentRequestDto.getBackgroundImageUuidName())
                .type(ImageType.JPG).build());

        Club club = clubRepository.save(Club.builder()
                .name(clubEnrollmentRequestDto.getName())
                .introduction(clubEnrollmentRequestDto.getIntroduction())
                .leader(user)
                .dependent(clubEnrollmentRequestDto.getDependentType())
                .clubImage(clubImage)
                .backgroundImage(backgroundImage).build());

        List<String> tagNames = clubEnrollmentRequestDto.getTags();
        List<Tag> tags = tagRepository.findByNames(tagNames);
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

    public ClubEnrollmentResponseDto readClubEnrollment(Long enrollmentId) {
        Enrollment enrollments = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new RuntimeException("NO enrollment")); // 예외처리 수정 예정
        Club club = enrollments.getClub();
        Image clubImage = club.getClubImage();
        Image backgroundImage = club.getBackgroundImage();

        return ClubEnrollmentResponseDto.builder()
                .name(club.getName())
                .introduction(club.getIntroduction())
                .dependent(club.getDependent().getDescription())
                .clubImageUuidName(clubImage.getUuidName())
                .backgroundImageUuidName(backgroundImage.getUuidName())
                .tags(club.getTags().stream().map(
                        clubTag -> clubTag.getTag().getName())
                        .collect(Collectors.toList())
                )
                .build();
    }
}
