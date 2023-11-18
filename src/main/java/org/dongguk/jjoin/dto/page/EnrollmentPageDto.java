package org.dongguk.jjoin.dto.page;

import lombok.Builder;
import lombok.Getter;
import org.dongguk.jjoin.dto.response.EnrollmentDto;

import java.util.List;

@Getter
public class EnrollmentPageDto {
    private List<EnrollmentDto> data;
    private PageInfo pageInfo;

    @Builder
    public EnrollmentPageDto(List<EnrollmentDto> data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
