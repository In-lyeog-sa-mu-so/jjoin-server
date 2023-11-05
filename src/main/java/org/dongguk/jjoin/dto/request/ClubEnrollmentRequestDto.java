package org.dongguk.jjoin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dongguk.jjoin.domain.type.DependentType;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClubEnrollmentRequestDto {
    private String name;
    private String introduction;
    private DependentType dependentType;
    private String clubImageOriginName;
    private String clubImageUuidName;
    private String backgroundImageOriginName;
    private String backgroundImageUuidName;
    private List<String> tags;
}
