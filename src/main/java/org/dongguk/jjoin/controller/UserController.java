package org.dongguk.jjoin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.dto.request.UserProfileUpdateDto;
import org.dongguk.jjoin.dto.response.ClubCardDto;
import org.dongguk.jjoin.dto.response.ScheduleDayDto;
import org.dongguk.jjoin.dto.response.UserClubDto;
import org.dongguk.jjoin.dto.response.UserProfileDto;
import org.dongguk.jjoin.service.ClubService;
import org.dongguk.jjoin.service.ScheduleService;
import org.dongguk.jjoin.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final ClubService clubService;

    // 홈화면에서의 가입한 동아리 조회 API
    @GetMapping("/clubs")
    public Map<String, Object> readUserClubs() {
        Long userId = 1L;
        Map<String, Object> result = new HashMap<>();
        result.put("data", userService.readUserClubs(userId));
        return result;
    }

    // 홈화면에서의 사용자의 스케줄 조회 API
    @GetMapping("/schedules")
    public Map<String, Object> readUserSchedules() {
        Long userId = 1L;
        String targetDate = LocalDateTime.now().toString().substring(0, 10).replaceAll("-", "");
        Map<String, Object> result = new HashMap<>();
        result.put("data", scheduleService.readDaySchedules(userId, targetDate));
        return result;
    }

    // 사용자 조회 API
    @GetMapping("/{userId}")
    public UserProfileDto readUserProfile(@PathVariable Long userId) {
        return userService.readUserProfile(userId);
    }

    // 사용자 프로필 변경 API
    @PutMapping("/{userId}")
    public Boolean updateUserProfile(@PathVariable Long userId, @RequestPart UserProfileUpdateDto data,
                                     @RequestPart MultipartFile userProfileImageFile) {
        return userService.updateUserProfile(userId, data, userProfileImageFile);
    }

    // 회원 탈퇴 API
    @DeleteMapping("/{userId}")
    public Boolean deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    // 마이페이지에서의 사용자 동아리 조회 API
    @GetMapping("/{userId}/clubs")
    public Map<String, Object> readUserClubs(@PathVariable Long userId) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", clubService.readUserClubs(userId));
        return result;
    }
}
