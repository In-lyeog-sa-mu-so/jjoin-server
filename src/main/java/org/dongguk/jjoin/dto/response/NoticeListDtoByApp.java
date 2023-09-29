package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class NoticeListDtoByApp {
    private Long id;
    private String title;
    private String content;
    private Timestamp updatedDate;

    @Builder
    public NoticeListDtoByApp(Long id, String title, String content, Timestamp updatedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.updatedDate = updatedDate;
    }
}
