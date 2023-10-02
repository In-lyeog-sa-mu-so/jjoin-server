package org.dongguk.jjoin.repository;

import org.dongguk.jjoin.domain.Club;
import org.dongguk.jjoin.domain.ClubMember;
import org.dongguk.jjoin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {

    @Query(value = "SELECT c FROM ClubMember AS cm INNER JOIN Club AS c ON cm.club = c WHERE cm.user = :user")
    List<Club> findUserClubsByUser(@Param("user") User user);
    Long countAllByClub(Club club);
}
