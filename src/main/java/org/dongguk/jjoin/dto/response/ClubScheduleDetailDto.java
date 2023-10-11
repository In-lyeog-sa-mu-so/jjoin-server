package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ClubScheduleDetailDto {
    private Long planId;
    private String clubName;
    private String title;
    private String content;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Boolean isAgreed;

    @Builder
    public ClubScheduleDetailDto(Long planId, String clubName, String title, String content, Timestamp startDate, Timestamp endDate, Timestamp createdDate, Timestamp updatedDate, Boolean isAgreed) {
        this.planId = planId;
        this.clubName = clubName;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isAgreed = isAgreed;
    }
}