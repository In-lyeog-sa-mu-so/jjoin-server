package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Notice findFirstByClubOrderByCreatedDateDesc(Club club);

    @Query("SELECT n FROM Notice AS n WHERE n.club = :club AND n.isDeleted = FALSE")
    Page<Notice> findAllByClubAndNotDeleted(@Param("club") Club club, Pageable pageable);
}
