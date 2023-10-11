package org.dongguk.jjoin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.jjoin.domain.Album;
import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.dto.response.ClubAlbumDetailDto;
import org.dongguk.jjoin.dto.response.ClubAlbumDto;
import org.dongguk.jjoin.repository.AlbumRepository;
import org.dongguk.jjoin.repository.ClubRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ClubRepository clubRepository;

    public List<ClubAlbumDto> readClubAlbumList(Long clubId, Long page) {
        Club club = clubRepository.findById(clubId).get();
        List<Album> albumList = albumRepository.findByClub(club);
        List<ClubAlbumDto> clubAlbumDtoList = new ArrayList<>();

        for (Album album : albumList) {
            clubAlbumDtoList.add(ClubAlbumDto.builder()
                            .albumId(album.getId())
                            .thumbnailImageUuid(album.getImages().get(0).getUuidName())
                    .build());
        }

        return clubAlbumDtoList;
    }

    public ClubAlbumDetailDto readClubAlbumDetail(Long clubId, Long albumId) {
        Album album = albumRepository.findById(albumId).get();
        List<String> imageUuidList = new ArrayList<>();
        album.getImages().forEach(image -> imageUuidList.add(image.getUuidName()));

        return ClubAlbumDetailDto.builder()
                .albumId(albumId)
                .imageUuidList(imageUuidList)
                .createdDate(album.getCreateDate())
                .build();
    }
}
