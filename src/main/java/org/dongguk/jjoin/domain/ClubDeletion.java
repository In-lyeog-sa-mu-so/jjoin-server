package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "club_deletions")
public class ClubDeletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Builder
    public ClubDeletion(Club club) {
        this.club = club;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
    }

    public void deleteClub() {
        club.deleteClub();
    }
}
