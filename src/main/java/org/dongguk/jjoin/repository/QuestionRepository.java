package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Application_question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Application_question, Long> {
}
