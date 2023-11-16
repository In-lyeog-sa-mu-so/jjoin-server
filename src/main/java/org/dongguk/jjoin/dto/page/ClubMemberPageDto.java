package org.dongguk.jjoin.dto.page;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dongguk.jjoin.dto.response.ClubMemberDto;

import java.util.List;

@Data
@NoArgsConstructor
public class ClubMemberPageDto {
    private List<ClubMemberDto> data;
    private PageInfo pageInfo;

    @Builder
    public ClubMemberPageDto(List<ClubMemberDto> data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
