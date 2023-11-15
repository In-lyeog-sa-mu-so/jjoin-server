package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Application_question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Application_question, Long> {
    List<Application_question> findAllByClubId(Long clubId);
    Optional<Application_question> findById(Long id);

    @Query("SELECT q FROM Application_question AS q WHERE q.club.id = :clubId AND q.id = :id")
    Optional<Application_question> findByClubIdAndId(@Param("clubId") Long clubId, @Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM Application_question AS q WHERE q.club.id = :clubId AND q.id IN :ids")
    void deleteAllByClubIdAndId(@Param("clubId") Long clubId, @Param("ids") List<Long> ids);
}