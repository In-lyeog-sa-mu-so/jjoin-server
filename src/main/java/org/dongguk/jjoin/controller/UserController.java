package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.dto.response.ClubCardDto;
import org.dongguk.jjoin.dto.response.ScheduleDayDto;
import org.dongguk.jjoin.dto.response.UserClubDto;
import org.dongguk.jjoin.dto.response.UserProfileDto;
import org.dongguk.jjoin.service.ClubService;
import org.dongguk.jjoin.service.ScheduleService;
import org.dongguk.jjoin.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final ClubService clubService;

    @GetMapping("/clubs")
    public List<ClubCardDto> readUserClubs() {
        Long userId = 1L;
        return userService.readUserClubs(userId);
    }

    @GetMapping("/schedules")
    public List<ScheduleDayDto> readUserSchedules() {
        Long userId = 1L;
        return scheduleService.readDaySchedules(userId, LocalDateTime.now().toString().substring(0, 10).replaceAll("-", ""), true);
    }

    @GetMapping("/{userId}")
    public UserProfileDto readUserProfile(@PathVariable Long userId) {
        return userService.readUserProfile(userId);
    }

    @GetMapping("/{userId}/clubs")
    public List<UserClubDto> readUserClubs(@PathVariable Long userId) {
        return clubService.readUserClubList(userId);
    }
}
