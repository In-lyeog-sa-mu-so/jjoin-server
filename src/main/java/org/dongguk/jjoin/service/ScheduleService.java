package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Plan;
import org.dongguk.jjoin.domain.Schedule;
import org.dongguk.jjoin.domain.User;
import org.dongguk.jjoin.dto.request.ScheduleDecideDto;
import org.dongguk.jjoin.dto.response.ClubScheduleDetailDto;
import org.dongguk.jjoin.dto.response.ClubScheduleDto;
import org.dongguk.jjoin.dto.response.ScheduleDayDto;
import org.dongguk.jjoin.dto.response.ScheduleDaysDto;
import org.dongguk.jjoin.repository.ClubRepository;
import org.dongguk.jjoin.repository.PlanRepository;
import org.dongguk.jjoin.repository.ScheduleRepository;
import org.dongguk.jjoin.repository.UserRepository;
import org.dongguk.jjoin.util.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;
    private final PlanRepository planRepository;
    private final DateUtil dateUtil;

    public List<ScheduleDayDto> readDaySchedules(Long userId, String targetDate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        Timestamp date = dateUtil.stringToTimestamp(targetDate);
        List<Schedule> schedules = scheduleRepository.findAgreedPlansByDate(user, date);
        schedules.addAll(scheduleRepository.findUnplansByDate(user, date));

        List<ScheduleDayDto> scheduleDayDtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            Plan plan = schedule.getPlan();
            scheduleDayDtos.add(ScheduleDayDto.builder()
                    .id(schedule.getId())
                    .name(plan.getClub().getName())
                    .startDate(plan.getStartDate())
                    .endDate(plan.getEndDate())
                    .title(plan.getTitle())

                    .content(plan.getContent())
                    .isAgreed(schedule.getIsAgreed())
                    .build());
        }
        return scheduleDayDtos;
    }

    // 기간에 해당하는 일정 리스트 반환
    public List<ScheduleDaysDto> readPeriodPlans(Long userId, String startDate, String endDate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        List<Timestamp> dates = dateUtil.getDates(startDate, endDate);

        List<ScheduleDaysDto> scheduleDaysDtos = new ArrayList<>();
        for (Timestamp date : dates) {
            List<Schedule> schedules = scheduleRepository.findAgreedPlansByDate(user, date);
            List<ScheduleDayDto> scheduleDayDtos = new ArrayList<>();
            for (Schedule schedule : schedules) {
                Plan plan = schedule.getPlan();
                scheduleDayDtos.add(ScheduleDayDto.builder()
                        .id(schedule.getId())
                        .name(plan.getClub().getName())
                        .startDate(plan.getStartDate())
                        .endDate(plan.getEndDate())
                        .title(plan.getTitle())
                        .content(plan.getContent())
                        .isAgreed(schedule.getIsAgreed())
                        .build());
            }
            scheduleDaysDtos.add(ScheduleDaysDto.builder()
                    .date(date)
                    .scheduleDayDtos(scheduleDayDtos)
                    .build());
        }
        return scheduleDaysDtos;
    }

    public Boolean updateSchedule(Long scheduleId, ScheduleDecideDto scheduleDecideDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId).get();
        schedule.scheduleDecidedBy(scheduleDecideDto.getIsAgreed());

        return Boolean.TRUE;
    }

    // 특정 동아리의 일정 목록 반환
    public List<ClubScheduleDto> readClubSchedules(Long userId, Long clubId, Long page, Long size) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new RuntimeException("no match clubId"));
        PageRequest pageable = PageRequest.of(page.intValue(), size.intValue(), Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Plan> plans = planRepository.findByClub(club, pageable);
        List<ClubScheduleDto> clubScheduleDtos = new ArrayList<>();

        for (Plan plan : plans.getContent()) {
            Schedule schedule = scheduleRepository.findByUserAndPlan(user, plan);
            clubScheduleDtos.add(ClubScheduleDto.builder()
                    .id(schedule.getId())
                    .startDate(plan.getStartDate())
                    .endDate(plan.getEndDate())
                    .title(plan.getTitle())
                    .content(plan.getContent())
                    .isAgreed(schedule.getIsAgreed())
                    .build());
        }
        return clubScheduleDtos;
    }

    public ClubScheduleDetailDto readClubScheduleDetail(Long clubId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).get();
        Plan plan = schedule.getPlan();

        return ClubScheduleDetailDto.builder()
                .id(schedule.getId())
                .name(clubRepository.findById(clubId).get().getName())
                .title(plan.getTitle())
                .content(plan.getContent())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .createdDate(plan.getCreatedDate())
                .updatedDate(plan.getUpdatedDate())
                .isAgreed(schedule.getIsAgreed())
                .build();
    }
}
