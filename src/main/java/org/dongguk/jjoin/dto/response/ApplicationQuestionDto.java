package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApplicationQuestionDto {
    private Long id;
    private String content;
    @Builder
    public ApplicationQuestionDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
