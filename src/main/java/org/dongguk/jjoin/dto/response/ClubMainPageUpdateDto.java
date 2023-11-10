package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ClubMainPageUpdateDto {
    private String introduction;
    private Boolean isFinished;
    private Timestamp startDate;
    private Timestamp endDate;

    @Builder
    public ClubMainPageUpdateDto(String introduction, boolean isFinished, Timestamp startDate, Timestamp endDate) {
        this.introduction = introduction;
        this.isFinished = isFinished;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
