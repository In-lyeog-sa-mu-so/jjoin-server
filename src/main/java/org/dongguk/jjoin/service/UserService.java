package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Image;
import org.dongguk.jjoin.domain.User;
import org.dongguk.jjoin.domain.type.ImageType;
import org.dongguk.jjoin.dto.request.UserProfileUpdateDto;
import org.dongguk.jjoin.dto.response.ClubCardDto;
import org.dongguk.jjoin.dto.response.UserProfileDto;
import org.dongguk.jjoin.repository.*;
import org.dongguk.jjoin.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final NoticeRepository noticeRepository;
    private final ImageRepository imageRepository;
    private final FileUtil fileUtil;

    // 사용자가 가입한 동아리 반환
    public List<ClubCardDto> readUserClubs(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException()); // 예외처리 수정 예정
        List<Club> clubs = clubMemberRepository.findUserClubsByUser(user);
        List<ClubCardDto> clubCardDtos = new ArrayList<>();

        for (Club club : clubs) {
            clubCardDtos.add(ClubCardDto.builder()
                    .id(club.getId())
                    .name(club.getName())
                    .introduction(club.getIntroduction())
                    .leaderName(club.getLeader().getName())
                    .numberOfMembers(clubMemberRepository.countAllByClub(club))
                    .dependent(club.getDependent().toString())
                    .profileImageUuid(club.getClubImage().getUuidName())
                    .newestNotice(noticeRepository.findFirstByClubOrderByCreatedDateDesc(club).getTitle())
                    .build());
        }
        return clubCardDtos;
    }

    public UserProfileDto readUserProfile(Long userId) {
        User user = userRepository.findById(userId).get();

        return UserProfileDto.builder()
                .id(user.getId())
                .studentId(user.getStudentId())
                .profileImageUuid(user.getProfileImage().getUuidName())
                .name(user.getName())
                .major(user.getMajor().toString())
                .introduction(user.getIntroduction())
                .build();
    }

    public Boolean updateUserProfile(Long userId, UserProfileUpdateDto userProfileUpdateDto,
                                     MultipartFile userProfileImageFile) {
        User user = userRepository.findById(userId).get();
        String userProfileImageOriginName = userProfileImageFile.getOriginalFilename();
        String userProfileImageUuidName = fileUtil.storeFile(userProfileImageFile);
        Image userProfileImage = imageRepository.save(Image.builder()
                .user(user)
                .album(null)
                .notice(null)
                .originName(userProfileImageOriginName)
                .uuidName(userProfileImageUuidName)
                .type(ImageType.valueOf(fileUtil.getFileExtension(userProfileImageOriginName).toUpperCase()))
                .build());
        user.updateUserProfile(userProfileUpdateDto.getIntroduction(), userProfileImage);

        return Boolean.TRUE;
    }

    public Boolean deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return Boolean.TRUE;
    }
}
