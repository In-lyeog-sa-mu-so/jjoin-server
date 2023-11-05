package org.dongguk.jjoin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dongguk.jjoin.domain.type.DependentType;

import java.util.List;

@Getter
public class ClubEnrollmentResponseDto {
    private String name;
    private String introduction;
    private String dependentType;
    private String clubImageOriginName;
    private String clubImageUuidName;
    private String backgroundImageOriginName;
    private String backgroundImageUuidName;
    private List<String> tags;

    @Builder
    public ClubEnrollmentResponseDto(String name, String introduction, String dependentType, String clubImageOriginName, String clubImageUuidName, String backgroundImageOriginName, String backgroundImageUuidName, List<String> tags) {
        this.name = name;
        this.introduction = introduction;
        this.dependentType = dependentType;
        this.clubImageOriginName = clubImageOriginName;
        this.clubImageUuidName = clubImageUuidName;
        this.backgroundImageOriginName = backgroundImageOriginName;
        this.backgroundImageUuidName = backgroundImageUuidName;
        this.tags = tags;
    }
}
