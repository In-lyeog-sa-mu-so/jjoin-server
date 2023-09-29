package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Notice;
import org.dongguk.jjoin.dto.response.NoticeListDtoByApp;
import org.dongguk.jjoin.repository.ClubRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClubService {
    private final ClubRepository clubRepository;

    // 동아리 게시글(공지, 홍보) 목록을 보여주는 API
    public List<NoticeListDtoByApp> showNoticeList(Long clubId, Integer page, Integer size){
        Club club = clubRepository.findById(clubId).orElseThrow(()-> new RuntimeException("no match clubId"));
        List<Notice> notices = Optional.ofNullable(club.getNotices()).orElseThrow(()-> new RuntimeException("Notice Not found!"));
        notices.removeIf(notice -> notice.isDeleted());
        notices.sort(Comparator.comparing(Notice::getUpdatedDate).reversed());

        int startIdx = page * size;
        List<Notice> showNotices = notices.subList(startIdx, Math.min(startIdx + size, notices.size()));
        List<NoticeListDtoByApp> noticeListDtoByApps = new ArrayList<>();
        for (Notice n : showNotices){
            noticeListDtoByApps.add(NoticeListDtoByApp.builder()
                            .id(n.getId())
                            .title(n.getTitle())
                            .content(n.getContent())
                            .updatedDate(n.getUpdatedDate()).build());
        }
        return noticeListDtoByApps;
    }
}
