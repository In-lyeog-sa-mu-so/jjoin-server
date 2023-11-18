package org.dongguk.jjoin.dto.page;

import lombok.Builder;
import lombok.Getter;
import org.dongguk.jjoin.dto.response.ClubEnrollmentDto;

import java.util.List;

@Getter
public class ClubEnrollmentPageDto {
    private List<ClubEnrollmentDto> data;
    private PageInfo pageInfo;

    @Builder
    public ClubEnrollmentPageDto(List<ClubEnrollmentDto> data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
