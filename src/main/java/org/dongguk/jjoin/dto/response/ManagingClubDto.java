package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ManagingClubDto {
    private Long id;
    private String name;
    private String introduction;
    private String leaderName;
    private Long numberOfMembers;
    private String dependent;
    private String profileImageUuid;

    @Builder
    public ManagingClubDto(Long id, String name, String introduction, String leaderName, Long numberOfMembers, String dependent, String profileImageUuid) {
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.leaderName = leaderName;
        this.numberOfMembers = numberOfMembers;
        this.dependent = dependent;
        this.profileImageUuid = profileImageUuid;
    }
}
