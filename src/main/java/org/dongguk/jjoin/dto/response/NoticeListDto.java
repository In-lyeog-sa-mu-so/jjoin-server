package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class NoticeListDto {
    private Long id;
    private Long noticeNumber;
    private String title;
    private Timestamp createdDate;

    @Builder
    public NoticeListDto(Long id, Long noticeNumber, String title, Timestamp createdDate) {
        this.id = id;
        this.noticeNumber = noticeNumber;
        this.title = title;
        this.createdDate = createdDate;
    }
}
