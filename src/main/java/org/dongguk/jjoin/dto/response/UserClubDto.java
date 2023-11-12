package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserClubDto {
    private Long id;
    private String clubImageUuid;
    private String name;

    @Builder
    public UserClubDto(Long id, String clubImageUuid, String name) {
        this.id = id;
        this.clubImageUuid = clubImageUuid;
        this.name = name;
    }
}
