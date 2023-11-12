package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ClubRecommendDto {
    private Long id;
    private String name;
    private String introduction;
    private String profileImageUuid;
    private Long numberOfMembers;
    private String dependent;
    private List<String> tags;

    @Builder
    public ClubRecommendDto(Long id, String name, String introduction, Long numberOfMembers, String dependent, String profileImageUuid, List<String> tags) {
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.numberOfMembers = numberOfMembers;
        this.dependent = dependent;
        this.profileImageUuid = profileImageUuid;
        this.tags = tags;
    }
}
