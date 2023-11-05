package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Application_answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Application_answer, Long> {
    Application_answer findAllByApplicationQuestionId(Long questionId);
}
