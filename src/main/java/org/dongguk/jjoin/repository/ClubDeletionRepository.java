package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.ClubDeletion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubDeletionRepository extends JpaRepository<ClubDeletion, Long> {
}
