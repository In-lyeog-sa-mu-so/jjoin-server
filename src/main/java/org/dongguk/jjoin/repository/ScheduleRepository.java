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
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Schedule findByUserAndPlan(User user, Plan plan);

    @Query(value = "SELECT s FROM Schedule AS s INNER JOIN Plan AS p ON s.plan = p " +
            "WHERE s.user = :user AND s.isAgreed = TRUE AND :targetDate BETWEEN p.startDate AND p.endDate")
    List<Schedule> findAgreedPlansByDate(@Param("user") User user, @Param("targetDate") Timestamp targetDate);

    @Query(value = "SELECT s FROM Schedule AS s INNER JOIN Plan AS p ON s.plan = p " +
            "WHERE s.user = :user AND s.isAgreed = null AND p.startDate >= :targetDate")
    List<Schedule> findUnplansByDate(@Param("user") User user, @Param("targetDate") Timestamp targetDate);
}