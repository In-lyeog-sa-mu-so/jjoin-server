package org.dongguk.jjoin.dto.page;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dongguk.jjoin.dto.response.ApplicationDto;

import java.util.List;

@Data
@NoArgsConstructor
public class ApplicationPageDto {
    private List<ApplicationDto> data;
    private PageInfo pageInfo;

    @Builder
    public ApplicationPageDto(List<ApplicationDto> data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
