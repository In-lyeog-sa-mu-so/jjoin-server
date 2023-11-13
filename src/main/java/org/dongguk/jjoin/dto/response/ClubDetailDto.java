package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class ClubDetailDto {
    private Long id;
    private String name;
    private List<String> tags;
    private String introduction;
    private String leaderName;
    private Long numberOfMembers;
    private String dependent;
    private String profileImageUuid;
    private String backgroundImageUuid;
    private Timestamp createdDate;
    private Timestamp startDate;
    private Timestamp endDate;
    private Boolean isFinished;

    @Builder
    public ClubDetailDto(Long id, String name, List<String> tags, String introduction, String leaderName, Long numberOfMembers, String dependent, String backgroundImageUuid, String profileImageUuid, Timestamp createdDate, Timestamp startDate, Timestamp endDate, Boolean isFinished) {
        this.id = id;
        this.name = name;
        this.tags = tags;
        this.introduction = introduction;
        this.leaderName = leaderName;
        this.numberOfMembers = numberOfMembers;
        this.dependent = dependent;
        this.backgroundImageUuid = backgroundImageUuid;
        this.profileImageUuid = profileImageUuid;
        this.createdDate = createdDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFinished = isFinished;
    }
}
