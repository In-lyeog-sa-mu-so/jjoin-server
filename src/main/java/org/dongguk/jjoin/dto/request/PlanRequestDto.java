package org.dongguk.jjoin.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PlanRequestDto {
    private Long clubId;
    private String title;
    private String content;
    private Timestamp startDate;
    private Timestamp endDate;

    @Builder
    public PlanRequestDto(Long clubId, String title, String content, Timestamp startDate, Timestamp endDate) {
        this.clubId = clubId;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
