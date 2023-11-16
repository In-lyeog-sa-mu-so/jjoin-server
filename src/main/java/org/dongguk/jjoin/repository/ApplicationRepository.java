package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.ClubApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<ClubApplication, Long> {
    @Query("SELECT ca FROM ClubApplication AS ca WHERE ca.club.id = :clubId ORDER BY ca.requestDate")
    Page<ClubApplication> findApplicationList(@Param("clubId") Long clubId, Pageable pageable);

    Optional<ClubApplication> findById(Long clubApplication_id);

    void deleteById(Long applicationId);
}
