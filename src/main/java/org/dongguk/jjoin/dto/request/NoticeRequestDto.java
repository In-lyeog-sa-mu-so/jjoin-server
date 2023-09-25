package org.dongguk.jjoin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeRequestDto {
    private String title;
    private String content;
    private Boolean isPrivate;
}
