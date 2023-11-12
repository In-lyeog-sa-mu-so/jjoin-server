package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileDto {
    private Long id;
    private String profileImageUuid;
    private String name;
    private String major;
    private String introduction;

    @Builder
    public UserProfileDto(Long id, String profileImageUuid, String name, String major, String introduction) {
        this.id = id;
        this.profileImageUuid = profileImageUuid;
        this.name = name;
        this.major = major;
        this.introduction = introduction;
    }
}
