package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class ClubAlbumDetailDto {
    private Long albumId;
    private List<String> imageUuidList;
    private Timestamp createdDate;

    @Builder
    public ClubAlbumDetailDto(Long albumId, List<String> imageUuidList, Timestamp createdDate) {
        this.albumId = albumId;
        this.imageUuidList = imageUuidList;
        this.createdDate = createdDate;
    }
}
