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
    private String dependent;
    private String clubImageUuidName;
    private String backgroundImageUuidName;
    private List<String> tags;

    @Builder
    public ClubEnrollmentResponseDto(String name, String introduction, String dependent, String clubImageUuidName, String backgroundImageUuidName, List<String> tags) {
        this.name = name;
        this.introduction = introduction;
        this.dependent = dependent;
        this.clubImageUuidName = clubImageUuidName;
        this.backgroundImageUuidName = backgroundImageUuidName;
        this.tags = tags;
    }
}
