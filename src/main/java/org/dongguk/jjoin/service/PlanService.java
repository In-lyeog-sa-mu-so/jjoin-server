package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Plan;
import org.dongguk.jjoin.dto.response.PlanDto;
import org.dongguk.jjoin.repository.ClubRepository;
import org.dongguk.jjoin.repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PlanService {
    private final PlanRepository planRepository;
    private final ClubRepository clubRepository;

    public List<PlanDto> readPlanList(Long clubId) {
        Club club = clubRepository.findById(clubId).get();
        List<Plan> planList = planRepository.findByClub(club);
        List<PlanDto> planDtoList = new ArrayList<>();

        for (Plan plan : planList) {
            planDtoList.add(PlanDto.builder()
                            .id(plan.getId())
                            .clubName(club.getName())
                            .title(plan.getTitle())
                            .content(plan.getContent())
                            .startDate(plan.getStartDate())
                            .endDate(plan.getEndDate())
                            .updatedDate(plan.getUpdatedDate())
                    .build());
        }

        return  planDtoList;
    }
}
