package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ClubScheduleDetailDto {
    private Long id;
    private String name;
    private String title;
    private String content;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Boolean isAgreed;

    @Builder
    public ClubScheduleDetailDto(Long id, String name, String title, String content, Timestamp startDate, Timestamp endDate, Timestamp createdDate, Timestamp updatedDate, Boolean isAgreed) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isAgreed = isAgreed;
    }
}