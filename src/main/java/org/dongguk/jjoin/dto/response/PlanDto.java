package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PlanDto {
    private Long id;
    private String title;
    private String content;
    private Timestamp startDate;
    private Timestamp endDate;

    @Builder
    public PlanDto(Long id, String title, String content, Timestamp startDate, Timestamp endDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
