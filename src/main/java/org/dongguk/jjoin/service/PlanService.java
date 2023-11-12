package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.*;
import org.dongguk.jjoin.dto.request.PlanRequestDto;
import org.dongguk.jjoin.dto.request.PlanUpdateDto;
import org.dongguk.jjoin.dto.response.PlanDto;
import org.dongguk.jjoin.repository.ClubMemberRepository;
import org.dongguk.jjoin.repository.ClubRepository;
import org.dongguk.jjoin.repository.PlanRepository;
import org.dongguk.jjoin.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PlanService {
    private final PlanRepository planRepository;
    private final ScheduleRepository scheduleRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;

    // 특정 동아리의 일정 목록 반환
    public List<PlanDto> readPlans(Long clubId) {
        Club club = clubRepository.findById(clubId).get();
        List<Plan> plans = planRepository.findByClub(club);
        List<PlanDto> planDtos = new ArrayList<>();

        for (Plan plan : plans) {
            planDtos.add(PlanDto.builder()
                    .id(plan.getId())
                    .title(plan.getTitle())
                    .content(plan.getContent())
                    .startDate(plan.getStartDate())
                    .endDate(plan.getEndDate())
                    .build());
        }
        return planDtos;
    }

    // 특정 동아리의 일정 등록
    public Boolean createPlan(Long clubId, PlanRequestDto planRequestDto) {
        Club club = clubRepository.findById(clubId).get();

        Plan plan = planRepository.save(Plan.builder()
                .club(club)
                .title(planRequestDto.getTitle())
                .content(planRequestDto.getContent())
                .startDate(planRequestDto.getStartDate())
                .endDate(planRequestDto.getEndDate())
                .build());

        List<User> users = clubMemberRepository.findByClub(club).stream()
                .map(cm -> cm.getUser()).collect(Collectors.toList());
        for (User user : users) {
            scheduleRepository.save(Schedule.builder()
                    .user(user)
                    .plan(plan)
                    .build());
        }
        return Boolean.TRUE;
    }

    // 특정 동아리의 일정 상세 정보 반환
    public PlanDto readPlanDetail(Long clubId, Long planId) {
        Plan plan = planRepository.findById(planId).get();

        return PlanDto.builder()
                .id(plan.getId())
                .title(plan.getTitle())
                .content(plan.getContent())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .build();
    }

    // 특정 동아리의 일정 정보 수정
    public Boolean updatePlan(Long clubId, Long planId, PlanUpdateDto planRequestDto) {
        Plan plan = planRepository.findById(planId).get();
        plan.updatePlan(planRequestDto);

        return Boolean.TRUE;
    }

    // 특정 동아리의 일정 삭제
    public Boolean deletePlan(Long clubId, Long planId) {
        planRepository.deleteById(planId);
        return Boolean.TRUE;
    }
}
