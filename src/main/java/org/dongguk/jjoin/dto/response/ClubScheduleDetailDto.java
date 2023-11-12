package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ClubScheduleDetailDto {
    private Long id;
    private String title;
    private String content;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Boolean isAgreed;
    private Long numberOfAgree;
    private Long numberOfDisagree;

    @Builder
    public ClubScheduleDetailDto(Long id, String title, String content, Timestamp startDate, Timestamp endDate, Timestamp createdDate, Timestamp updatedDate, Boolean isAgreed, Long numberOfAgree, Long numberOfDisagree) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isAgreed = isAgreed;
        this.numberOfAgree = numberOfAgree;
        this.numberOfDisagree = numberOfDisagree;
    }
}