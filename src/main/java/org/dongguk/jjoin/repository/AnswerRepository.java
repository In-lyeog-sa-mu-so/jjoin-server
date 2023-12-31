package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Application_answer;
import org.dongguk.jjoin.domain.Application_question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Application_answer, Long> {
    Application_answer findAllByApplicationQuestionIdAndUserId(Long questionId, Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM Application_answer AS ans WHERE ans.user.id = :userId AND ans.applicationQuestion IN :questions")
    void deleteAllByUserAndQuestion(@Param("userId") Long userId, @Param("questions") List<Application_question> questions);
}
