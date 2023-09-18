package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Notice;
import org.dongguk.jjoin.dto.request.NoticeRequestDto;
import org.dongguk.jjoin.dto.response.NoticeDto;
import org.dongguk.jjoin.repository.ClubRepository;
import org.dongguk.jjoin.repository.NoticeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ManagerService {
    private final ClubRepository clubRepository;
    private final NoticeRepository noticeRepository;

    public void createNotice(Long userId, Long clubId, NoticeRequestDto noticeRequestDto){
        // 유저 유무, 클럽 존재유무 확인
        //User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("createNotice club없음!"));
        Club club = clubRepository.findById(clubId).orElseThrow(()-> new RuntimeException("createNotice club없음!"));

        noticeRepository.save(Notice.builder()
                        .title(noticeRequestDto.getTitle())
                        .isPrivate(true)
                        .content(noticeRequestDto.getContent())
                        .club(club).build());
    }

    public NoticeDto readNotice(Long clubId, Long noticeId){
        Club club = clubRepository.findById(clubId).orElseThrow(()-> new RuntimeException("readNotice club없음"));
        Notice notice = club.getNotices().stream()
                            .filter(n -> n.getId().equals(noticeId)).findAny()
                            .orElseThrow(() -> new RuntimeException("No match noticeId"));

        return NoticeDto.builder()
                        .id(notice.getId())
                        .title(notice.getTitle())
                        .content(notice.getContent())
                        .createdDate(notice.getCreatedDate())
                        .updatedDate(notice.getUpdatedDate()).build();
    }
}
