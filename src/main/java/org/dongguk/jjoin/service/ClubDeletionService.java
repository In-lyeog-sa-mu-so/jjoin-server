package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.ClubDeletion;
import org.dongguk.jjoin.dto.page.ClubDeletionPageDto;
import org.dongguk.jjoin.dto.page.PageInfo;
import org.dongguk.jjoin.dto.request.ClubDeletionUpdateDto;
import org.dongguk.jjoin.dto.response.ClubDeletionDto;
import org.dongguk.jjoin.repository.ClubDeletionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ClubDeletionPageDto readClubDeletions(Long page, Long size) {
        PageRequest pageable = PageRequest.of(page.intValue(), size.intValue(), Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<ClubDeletion> clubDeletions = clubDeletionRepository.findAll(pageable);
        List<ClubDeletionDto> clubDeletionDtos = new ArrayList<>();

        for (ClubDeletion clubDeletion : clubDeletions.getContent()) {
            Club club = clubDeletion.getClub();
            clubDeletionDtos.add(ClubDeletionDto.builder()
                    .id(clubDeletion.getId())
                    .clubName(club.getName())
                    .dependent(club.getDependent().toString())
                    .tags(club.getTags().stream().map(clubTag -> clubTag.getTag().getName())
                            .collect(Collectors.toList()))
                    .createdDate(clubDeletion.getCreatedDate())
                    .build());
        }

        return ClubDeletionPageDto.builder()
                .data(clubDeletionDtos)
                .pageInfo(PageInfo.builder()
                        .page(page.intValue())
                        .size(size.intValue())
                        .totalElements(clubDeletions.getTotalElements())
                        .totalPages(clubDeletions.getTotalPages())
                        .build())
                .build();
    }

    // 동아리 삭제 신청 승인
    public Boolean updateClubDeletions(ClubDeletionUpdateDto clubDeletionUpdateDto) {
        List<Long> ids = clubDeletionUpdateDto.getIds();

        for (Long id : ids) {
            clubDeletionRepository.findById(id).get().deleteClub();
            clubDeletionRepository.deleteById(id);
        }
        return true;
    }
}
