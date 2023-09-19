package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.jjoin.domain.Plan;
import org.dongguk.jjoin.domain.Schedule;
import org.dongguk.jjoin.domain.User;
import org.dongguk.jjoin.dto.response.ScheduleDayDto;
import org.dongguk.jjoin.repository.ClubRepository;
import org.dongguk.jjoin.repository.ScheduleRepository;
import org.dongguk.jjoin.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final ClubRepository clubRepository;


    public List<ScheduleDayDto> readDaySchedules(Long userId, String targetDate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormat.setLenient(false);
        Timestamp stringToTimestamp = null;
        try {
            Date stringToDate = simpleDateFormat.parse(targetDate);
            stringToTimestamp = new Timestamp(stringToDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Plan> scheduleList = scheduleRepository.findPlansByDate(user, stringToTimestamp);
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
}
