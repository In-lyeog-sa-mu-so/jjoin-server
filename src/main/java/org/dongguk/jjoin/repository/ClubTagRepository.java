package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.ClubTag;
import org.dongguk.jjoin.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubTagRepository extends JpaRepository<ClubTag, Long> {

    @Query(value = "SELECT ct FROM ClubTag AS ct WHERE ct.tag.id = :tagId")
    List<ClubTag> findByTagId(@Param("tagId") Long tagId);

    List<ClubTag> findByClub(Club club);
}
