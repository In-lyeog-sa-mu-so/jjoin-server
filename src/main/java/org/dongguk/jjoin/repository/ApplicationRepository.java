package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.ClubApplication;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<ClubApplication, Long>{
    @Query("SELECT ca FROM ClubApplication AS ca WHERE ca.club.id = :clubId ORDER BY ca.requestDate")
    List<ClubApplication> findApplicationList(Long clubId, Pageable pageable);

    Optional<ClubApplication> findById(Long clubApplication_id);
}
