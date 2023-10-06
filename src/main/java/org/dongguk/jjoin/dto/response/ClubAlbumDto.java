package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClubAlbumDto {
    private Long albumId;
    private String thumbnailImageUuid;

    @Builder
    public ClubAlbumDto(Long albumId, String thumbnailImageUuid) {
        this.albumId = albumId;
        this.thumbnailImageUuid = thumbnailImageUuid;
    }
}
