package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ScheduleDayDto {
    private Long id;
    private String name;
    private Timestamp startDate;
    private Timestamp endDate;
    private String title;
    private String content;
    private Boolean isAgreed;

    @Builder
    public ScheduleDayDto(Long id, String name, Timestamp startDate, Timestamp endDate, String title, String content, Boolean isAgreed) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
        this.isAgreed = isAgreed;
    }
}
