package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.ClubDeletion;
import org.dongguk.jjoin.dto.request.ClubDeletionUpdateDto;
import org.dongguk.jjoin.dto.response.ClubDeletionDto;
import org.dongguk.jjoin.repository.ClubDeletionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClubDeletionService {
    private final ClubDeletionRepository clubDeletionRepository;

    // 동아리 삭제 신청서 반환
    public List<ClubDeletionDto> readClubDeletions() {
        List<ClubDeletion> clubDeletions = clubDeletionRepository.findByIsDeletedIsFalse();
        List<ClubDeletionDto> clubDeletionDtos = new ArrayList<>();

        for (ClubDeletion clubDeletion : clubDeletions) {
            Club club = clubDeletion.getClub();
            clubDeletionDtos.add(ClubDeletionDto.builder()
                            .id(clubDeletion.getId())
                            .clubName(club.getName())
                            .dependent(club.getDependent().toString())
                            .tags(club.getTags().stream().map(clubTag -> clubTag.getTag().getName())
                                    .collect(Collectors.toList()))
                            .createdDate(club.getCreatedDate())
                            .build());
        }
        return clubDeletionDtos;
    }

    public Boolean updateClubDeltionList(ClubDeletionUpdateDto clubDeletionUpdateDto) {
        List<Long> ids = clubDeletionUpdateDto.getId();

        for (Long id : ids) {
            clubDeletionRepository.findById(id).get().deleteClub();
        }

        return true;
    }
}
