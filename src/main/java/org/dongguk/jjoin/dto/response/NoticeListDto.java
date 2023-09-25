package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class NoticeListDto {
    private Long id;
    private String title;
    private Timestamp updatedDate;

    @Builder
    public NoticeListDto(Long id, String title, Timestamp updatedDate) {
        this.id = id;
        this.title = title;
        this.updatedDate = updatedDate;
    }
}
