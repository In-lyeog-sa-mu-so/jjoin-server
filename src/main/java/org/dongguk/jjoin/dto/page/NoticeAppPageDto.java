package org.dongguk.jjoin.dto.page;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dongguk.jjoin.dto.response.NoticeListDtoByApp;

import java.util.List;

@Data
@NoArgsConstructor
public class NoticeAppPageDto {
    private List<NoticeListDtoByApp> data;
    private PageInfo pageInfo;

    @Builder
    public NoticeAppPageDto(List<NoticeListDtoByApp> data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
