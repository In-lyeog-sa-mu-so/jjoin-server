package org.dongguk.jjoin.dto.page;

import lombok.Builder;
import lombok.Getter;
import org.dongguk.jjoin.dto.response.ClubScheduleDto;

import java.util.List;

@Getter
public class ClubSchedulePageDto {
    private List<ClubScheduleDto> data;
    private PageInfo pageInfo;

    @Builder
    public ClubSchedulePageDto(List<ClubScheduleDto> data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
