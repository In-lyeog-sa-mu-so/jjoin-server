package org.dongguk.jjoin.dto.response;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.dongguk.jjoin.domain.Image;
import org.dongguk.jjoin.domain.User;
import org.dongguk.jjoin.domain.type.DependentType;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class ClubDetailDto {
    private Long clubId;
    private String clubName;
    private List<String> tag;
    private String introduction;
    private String leaderName;
    private Long userNumber;
    private String dependent;
    private String backgroundImageUuid;
    private String profileImageUuid;
    private Timestamp createdDate;
    private Timestamp startDate;
    private Timestamp endDate;

    @Builder
    public ClubDetailDto(Long clubId, String clubName, List<String> tag, String introduction, String leaderName, Long userNumber, String dependent, String backgroundImageUuid, String profileImageUuid, Timestamp createdDate, Timestamp startDate, Timestamp endDate) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.tag = tag;
        this.introduction = introduction;
        this.leaderName = leaderName;
        this.userNumber = userNumber;
        this.dependent = dependent;
        this.backgroundImageUuid = backgroundImageUuid;
        this.profileImageUuid = profileImageUuid;
        this.createdDate = createdDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
