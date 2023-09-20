package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Plan;
import org.dongguk.jjoin.domain.Schedule;
import org.dongguk.jjoin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule findByUserAndPlan(@Param("user") User user, @Param("planId") Plan plan);


    @Query(value = "SELECT p FROM Schedule AS s INNER JOIN Plan AS p ON s.plan = p " +
            "WHERE s.user = :user AND p.endDate >= :targetDate")
    List<Plan> findPlansByDate(@Param("user") User user, @Param("targetDate") Timestamp targetDate);
}