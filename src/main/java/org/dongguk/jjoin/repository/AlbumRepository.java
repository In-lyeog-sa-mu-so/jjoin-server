package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Album;
import org.dongguk.jjoin.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByClub(Club club);
}
