package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClubCardDto {
    private Long id;
    private String name;
    private String introduction;
    private String leaderName;
    private Long numberOfMembers;
    private String dependent;
    private String profileImageUuid;
    private String newestNotice;

    @Builder
    public ClubCardDto(Long id, String name, String introduction, String leaderName, Long numberOfMembers, String dependent, String profileImageUuid, String newestNotice) {
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.leaderName = leaderName;
        this.numberOfMembers = numberOfMembers;
        this.dependent = dependent;
        this.profileImageUuid = profileImageUuid;
        this.newestNotice = newestNotice;
    }
}
