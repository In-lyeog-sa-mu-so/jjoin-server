package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByClub(Club club);
}
