package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class SearchClubDto {
    private Long clubId;
    private String clubName;
    private String introduction;
    private Long userNumber;
    private String leaderName;
    private String dependent;
    private String profileImageUuid;
    private Boolean isFinished;
    private Timestamp startDate;
    private Timestamp endDate;

    @Builder
    public SearchClubDto(Long clubId, String clubName, String introduction, Long userNumber, String leaderName, String dependent, String profileImageUuid, Timestamp startDate, Timestamp endDate, Boolean isFinished) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.introduction = introduction;
        this.userNumber = userNumber;
        this.leaderName = leaderName;
        this.dependent = dependent;
        this.profileImageUuid = profileImageUuid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFinished = isFinished;
    }
}
