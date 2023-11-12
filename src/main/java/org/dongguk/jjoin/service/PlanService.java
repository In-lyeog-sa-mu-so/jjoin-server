package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Plan;
import org.dongguk.jjoin.dto.request.PlanRequestDto;
import org.dongguk.jjoin.dto.request.PlanUpdateDto;
import org.dongguk.jjoin.dto.response.PlanDto;
import org.dongguk.jjoin.repository.ClubRepository;
import org.dongguk.jjoin.repository.PlanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PlanService {
    private final PlanRepository planRepository;
    private final ClubRepository clubRepository;

    public List<PlanDto> readPlanList(Long clubId, Long page, Long size) {
        Club club = clubRepository.findById(clubId).get();
        PageRequest pageable = PageRequest.of(page.intValue(), size.intValue(), Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Plan> plans = planRepository.findByClub(club, pageable);
        List<PlanDto> planDtoList = new ArrayList<>();

        for (Plan plan : plans.getContent()) {
            planDtoList.add(PlanDto.builder()
                            .id(plan.getId())
                            .name(club.getName())
                            .title(plan.getTitle())
                            .content(plan.getContent())
                            .startDate(plan.getStartDate())
                            .endDate(plan.getEndDate())
                            .updatedDate(plan.getUpdatedDate())
                    .build());
        }

        return  planDtoList;
    }

    public Boolean createPlan(Long clubId, PlanRequestDto planRequestDto) {
        Club club = clubRepository.findById(clubId).get();

        planRepository.save(Plan.builder()
                        .club(club)
                        .title(planRequestDto.getTitle())
                        .content(planRequestDto.getContent())
                        .startDate(planRequestDto.getStartDate())
                        .endDate(planRequestDto.getEndDate())
                .build());

        return Boolean.TRUE;
    }

    public Boolean updatePlan(PlanUpdateDto planRequestDto) {
        Plan plan = planRepository.findById(planRequestDto.getId()).get();
        plan.updatePlan(planRequestDto);

        return Boolean.TRUE;
    }

    public Boolean deletePlan(Long planId) {
        planRepository.deleteById(planId);
        return Boolean.TRUE;
    }
}
