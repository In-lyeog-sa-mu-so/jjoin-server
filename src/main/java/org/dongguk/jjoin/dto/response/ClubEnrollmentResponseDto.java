package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ClubEnrollmentResponseDto {
    private String name;
    private String introduction;
    private String dependent;
    private String clubImageUuid;
    private String backgroundImageUuid;
    private List<String> tags;

    @Builder
    public ClubEnrollmentResponseDto(String name, String introduction, String dependent, String clubImageUuid, String backgroundImageUuid, List<String> tags) {
        this.name = name;
        this.introduction = introduction;
        this.dependent = dependent;
        this.clubImageUuid = clubImageUuid;
        this.backgroundImageUuid = backgroundImageUuid;
        this.tags = tags;
    }
}
