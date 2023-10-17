package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dongguk.jjoin.domain.type.RankType;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "club_members")
public class ClubMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(name = "ranks", nullable = false)
    @Enumerated(EnumType.STRING)
    private RankType rankType;

    @Column(name = "register_date", nullable = false)
    private Timestamp registerDate;

    @Builder
    public ClubMember(User user, Club club, RankType rankType) {
        this.user = user;
        this.club = club;
        this.rankType = rankType;
        this.registerDate = Timestamp.valueOf(LocalDateTime.now());
    }

    public void modifyRank(RankType rankType){
        this.rankType = rankType;
    }
}
