package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.ClubMember;
import org.dongguk.jjoin.domain.User;
import org.dongguk.jjoin.domain.type.RankType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    @Query(value = "SELECT c FROM ClubMember AS cm INNER JOIN Club AS c ON cm.club = c WHERE cm.user = :user")
    List<Club> findUserClubsByUser(@Param("user") User user);

    Long countAllByClub(Club club);

    List<ClubMember> findByClubId(Long clubId, Pageable pageable);

    @Query("SELECT cm FROM ClubMember AS cm WHERE cm.club.id = :clubId AND cm.user.id = :userId")
    Optional<ClubMember> findClubMemberByClubIdAndUserId(Long clubId, Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM ClubMember AS cm WHERE cm.club.id = :clubId AND cm.user.id IN :userIds")
    void deleteAllByClubIdAndUserId(Long clubId, List<Long> userIds);

    @Query("SELECT cm.club FROM ClubMember AS cm WHERE cm.user.id = :userId AND cm.rankType != :rankType")
    List<Club> findClubMemberByUserIdAndRankType(Long userId, RankType rankType);

    List<ClubMember> findByClub(Club club);
}
