package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class SearchClubDto {
    private Long clubId;
    private String clubName;
    private String introduction;
    private Long userNumber;
    private String dependent;
    private String profileImageUuid;
    private boolean isFinished;
    private Timestamp startDate;
    private Timestamp endDate;

    @Builder
    public SearchClubDto(Long clubId, String clubName, String introduction, Long userNumber, String dependent, String profileImageUuid, boolean isFinished, Timestamp startDate, Timestamp endDate) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.introduction = introduction;
        this.userNumber = userNumber;
        this.dependent = dependent;
        this.profileImageUuid = profileImageUuid;
        this.isFinished = isFinished;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
