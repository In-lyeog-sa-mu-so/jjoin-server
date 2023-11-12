package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ClubScheduleDto {
    private Long id;
    private Timestamp startDate;
    private Timestamp endDate;
    private String title;
    private String content;
    private Boolean isAgreed;

    @Builder
    public ClubScheduleDto(Long id, Timestamp startDate, Timestamp endDate, String title, String content, Boolean isAgreed) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
        this.isAgreed = isAgreed;
    }
}
