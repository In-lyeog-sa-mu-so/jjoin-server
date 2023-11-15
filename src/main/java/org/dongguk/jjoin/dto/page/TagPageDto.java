package org.dongguk.jjoin.dto.page;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dongguk.jjoin.dto.response.TagDto;

import java.util.List;

@Data
@NoArgsConstructor
public class TagPageDto {
    private List<TagDto> data;
    private PageInfo pageInfo;

    @Builder
    public TagPageDto(List<TagDto> data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
