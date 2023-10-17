package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileDto {
    private Long userId;
    private String profileImageUuid;
    private String name;
    private String major;
    private String introduction;

    @Builder
    public UserProfileDto(Long userId, String profileImageUuid, String name, String major, String introduction) {
        this.userId = userId;
        this.profileImageUuid = profileImageUuid;
        this.name = name;
        this.major = major;
        this.introduction = introduction;
    }
}
