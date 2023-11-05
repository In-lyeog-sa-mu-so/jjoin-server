package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.ClubApplication;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ClubApplication, Long>{
    @Query("SELECT ca FROM ClubApplication AS ca WHERE ca.club = :club ORDER BY ca.requestDate")
    List<ClubApplication> findApplicationList(Club club, Pageable pageable);
}
