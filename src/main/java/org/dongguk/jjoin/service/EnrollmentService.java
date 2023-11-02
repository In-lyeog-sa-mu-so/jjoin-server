package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.ClubTag;
import org.dongguk.jjoin.domain.Enrollment;
import org.dongguk.jjoin.dto.request.EnrollmentUpdateDto;
import org.dongguk.jjoin.dto.response.EnrollmentDto;
import org.dongguk.jjoin.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
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
}
