package org.dongguk.jjoin.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PlanUpdateDto {
    private String title;
    private String content;
    private Timestamp startDate;
    private Timestamp endDate;

    @Builder
    public PlanUpdateDto(String title, String content, Timestamp startDate, Timestamp endDate) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
