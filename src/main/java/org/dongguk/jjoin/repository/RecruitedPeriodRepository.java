package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Recruited_period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruitedPeriodRepository extends JpaRepository<Recruited_period, Long> {
    Optional<Recruited_period> findByClub(Club club);
}
