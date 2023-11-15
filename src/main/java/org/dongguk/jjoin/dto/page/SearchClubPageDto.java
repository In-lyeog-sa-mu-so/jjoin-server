package org.dongguk.jjoin.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dongguk.jjoin.dto.response.SearchClubDto;

import java.util.List;

@Data
@NoArgsConstructor
public class SearchClubPageDto {
    private List<SearchClubDto> clubs;
    private PageInfo pageInfo;

    @Builder
    public SearchClubPageDto(List<SearchClubDto> clubs, PageInfo pageInfo) {
        this.clubs = clubs;
        this.pageInfo = pageInfo;
    }
}
