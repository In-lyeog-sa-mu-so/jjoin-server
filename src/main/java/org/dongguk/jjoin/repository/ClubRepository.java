package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    @Query("SELECT c FROM Club AS c JOIN ClubTag AS ct ON ct.club = c WHERE ct.tag IN :tagList AND c.isDeleted = FALSE " +
            "AND c.createdDate != null")
    List<Club> findClubsByTags(List<Tag> tagList);

    @Query("SELECT c FROM Club AS c JOIN ClubTag AS ct ON ct.club = c WHERE ct.tag IN :tagList " +
            "AND (c.name LIKE %:keyword% OR c.introduction LIKE %:keyword%) AND c.isDeleted = FALSE " +
            "AND c.createdDate != null")
    List<Club> findClubsByTagsAndKeyword(List<Tag> tagList, String keyword);

    // 검색 키워드로 클럽 검색
    List<Club> findClubsByNameContainingOrIntroductionContainingIsDeletedIsFalseAndCreatedDateIsNotNull(String keyword, String keyword2);
}