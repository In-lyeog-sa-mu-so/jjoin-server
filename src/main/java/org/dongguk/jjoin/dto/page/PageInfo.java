package org.dongguk.jjoin.dto.page;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageInfo {
    private int page;
    private int size;
    private Long totalElements;
    private int totalPages;

    @Builder
    public PageInfo(int page, int size, Long totalElements, int totalPages) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
