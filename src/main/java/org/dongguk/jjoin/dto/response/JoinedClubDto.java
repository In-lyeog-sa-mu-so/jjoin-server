package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinedClubDto {
    private Long id;
    private String name;

    @Builder
    public JoinedClubDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
