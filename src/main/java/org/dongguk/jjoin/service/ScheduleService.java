package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.domain.Plan;
import org.dongguk.jjoin.domain.Schedule;
import org.dongguk.jjoin.domain.User;
import org.dongguk.jjoin.dto.request.ScheduleDecideDto;
import org.dongguk.jjoin.dto.response.ScheduleDayDto;
import org.dongguk.jjoin.dto.response.ScheduleDaysDto;
import org.dongguk.jjoin.repository.ScheduleRepository;
import org.dongguk.jjoin.repository.UserRepository;
import org.dongguk.jjoin.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public List<ScheduleDayDto> readDaySchedules(Long userId, String targetDate, boolean readUnplans) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        Timestamp date = DateUtil.stringToTimestamp(targetDate);

        List<Plan> scheduleList = scheduleRepository.findAgreedPlansByDate(user, date);
        if (readUnplans == true)
            scheduleList.addAll(scheduleRepository.findUnplansByDate(user, date));

        List<ScheduleDayDto> scheduleDayDtoList = new ArrayList<>();
        for (Plan plan : scheduleList) {
            Schedule schedule = scheduleRepository.findByUserAndPlan(user, plan);
            scheduleDayDtoList.add(ScheduleDayDto.builder()
                    .plan_id(plan.getId())
                    .club_name(plan.getClub().getName())
                    .start_date(plan.getStartDate())
                    .end_date(plan.getEndDate())
                    .title(plan.getTitle())
                    .content(plan.getContent())
                    .is_agreed(schedule.getIsAgreed())
                    .build());
        }

        return scheduleDayDtoList;
    }

    public List<ScheduleDaysDto> readWeekSchedules(Long userId, String dateWeek) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        List<Timestamp> timestampList = DateUtil.weekDays(dateWeek);

        List<ScheduleDaysDto> scheduleDaysDtoList = new ArrayList<>();
        for (Timestamp date : timestampList) {
            scheduleDaysDtoList.add(ScheduleDaysDto.builder()
                    .date(date)
                    .scheduleDayDtoList(readDaySchedules(userId, DateUtil.timestampToString(date), true))
                    .build());
        }

        return scheduleDaysDtoList;
    }

    public List<ScheduleDaysDto> readMonthSchedules(Long userId, String dateMonth) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        List<Timestamp> timestampList = DateUtil.monthDays(dateMonth);

        List<ScheduleDaysDto> scheduleDaysDtoList = new ArrayList<>();
        for (Timestamp date : timestampList) {
            scheduleDaysDtoList.add(ScheduleDaysDto.builder()
                    .date(date)
                    .scheduleDayDtoList(readDaySchedules(userId, DateUtil.timestampToString(date), false))
                    .build());
        }

        return scheduleDaysDtoList;
    }

    public Boolean updateSchedule(Long scheduleId, ScheduleDecideDto scheduleDecideDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId).get();
        schedule.setIsAgreed(scheduleDecideDto.getAcceptCheck());

        return true;
    }
}
