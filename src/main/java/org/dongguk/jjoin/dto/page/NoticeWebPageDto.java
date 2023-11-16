package org.dongguk.jjoin.dto.page;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dongguk.jjoin.dto.response.NoticeListDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
@NoArgsConstructor
public class NoticeWebPageDto {
    private List<NoticeListDto> data;
    private PageInfo pageInfo;

    @Builder
    public NoticeWebPageDto(List<NoticeListDto> data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
