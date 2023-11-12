package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PlanDto {
    private Long id;
    private String name;
    private String title;
    private String content;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp updatedDate;

    @Builder
    public PlanDto(Long id, String name, String title, String content, Timestamp startDate, Timestamp endDate, Timestamp updatedDate) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.updatedDate = updatedDate;
    }
}
