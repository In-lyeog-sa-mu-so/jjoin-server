package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.sql.Timestamp;

@Getter
public class NoticeDto {
    private Long id;
    private String title;
    private String content;
    private Timestamp createdDate;
    private Timestamp updatedDate;

    @Builder
    public NoticeDto(Long id, String title, String content, Timestamp createdDate, Timestamp updatedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
