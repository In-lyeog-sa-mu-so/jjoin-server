package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileDto {
    private Long id;
    private Long studentId;
    private String profileImageUuid;
    private String name;
    private String major;
    private String introduction;

    @Builder
    public UserProfileDto(Long id, Long studentId, String profileImageUuid, String name, String major, String introduction) {
        this.id = id;
        this.studentId = studentId;
        this.profileImageUuid = profileImageUuid;
        this.name = name;
        this.major = major;
        this.introduction = introduction;
    }
}
