package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClubDto {
    private Long clubId;
    private String clubName;
    private String introduction;
    private String leaderName;
    private Long userNumber;
    private String dependent;
    private String profileImageUuid;
    private String newestNotice;

    @Builder
    public ClubDto(Long clubId, String clubName, String introduction, String leaderName, Long userNumber, String dependent, String profileImageUuid, String newestNotice) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.introduction = introduction;
        this.leaderName = leaderName;
        this.userNumber = userNumber;
        this.dependent = dependent;
        this.profileImageUuid = profileImageUuid;
        this.newestNotice = newestNotice;
    }
}
