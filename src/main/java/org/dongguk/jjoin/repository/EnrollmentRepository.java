package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Enrollment;
import org.dongguk.jjoin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query(value = "SELECT e FROM Enrollment AS e WHERE e.club.leader = :user")
    List<Enrollment> findByUser(@Param("user") User user);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM Enrollment AS e WHERE e.id in :ids")
    void deleteByIds(@Param("ids") List<Long> ids);
}
