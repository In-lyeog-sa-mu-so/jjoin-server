package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ApplicationDetailDto {
    private ApplicationDto applicationDto;
    private List<ApplicationQAset> applicationQAsets;

    @Builder
    public ApplicationDetailDto(ApplicationDto applicationDto, List<ApplicationQAset> applicationQAsets) {
        this.applicationDto = applicationDto;
        this.applicationQAsets = applicationQAsets;
    }
}
