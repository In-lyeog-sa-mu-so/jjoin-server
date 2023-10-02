package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ClubRecommendDto {
    private Long clubId;
    private String clubName;
    private String introduction;
    private String profileImageUuid;
    private Long userNumber;
    private String dependent;
    private List<String> tags;

    @Builder
    public ClubRecommendDto(Long clubId, String clubName, String introduction, Long userNumber, String dependent, String profileImageUuid, List<String> tags) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.introduction = introduction;
        this.userNumber = userNumber;
        this.dependent = dependent;
        this.profileImageUuid = profileImageUuid;
        this.tags = tags;
    }
}
