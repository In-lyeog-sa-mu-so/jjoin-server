package org.dongguk.jjoin.dto.page;

import lombok.Builder;
import lombok.Getter;
import org.dongguk.jjoin.dto.response.ClubDeletionDto;

import java.util.List;

@Getter
public class ClubDeletionPageDto {
    private List<ClubDeletionDto> data;
    private PageInfo pageInfo;

    @Builder
    public ClubDeletionPageDto(List<ClubDeletionDto> data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
