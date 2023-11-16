package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query("SELECT c FROM Club AS c JOIN ClubTag AS ct ON ct.club = c WHERE ct.tag IN :tagList AND c.isDeleted = FALSE " +
            "AND c.createdDate != null")
    Page<Club> findClubsByTags(@Param("tagList") List<Tag> tagList, Pageable pageable);

    @Query("SELECT c FROM Club AS c JOIN ClubTag AS ct ON ct.club = c WHERE ct.tag IN :tagList " +
            "AND (c.name LIKE %:keyword% OR c.introduction LIKE %:keyword%) AND c.isDeleted = FALSE " +
            "AND c.createdDate != null")
    Page<Club> findClubsByTagsAndKeyword(@Param("tagList") List<Tag> tagList, @Param("keyword") String keyword, Pageable pageable);

    // 검색 키워드로 클럽 검색
    Page<Club> findClubsByNameContainingOrIntroductionContainingAndIsDeletedIsFalseAndCreatedDateIsNotNull(String keyword, String keyword2, Pageable pageable);
    Page<Club> findAll(Pageable pageable);
}