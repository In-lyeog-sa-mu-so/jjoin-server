package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserClubDto {
    private Long clubId;
    private String clubImage;
    private String clubName;

    @Builder
    public UserClubDto(Long clubId, String clubImage, String clubName) {
        this.clubId = clubId;
        this.clubImage = clubImage;
        this.clubName = clubName;
    }
}
