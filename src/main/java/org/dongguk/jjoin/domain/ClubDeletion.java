package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Builder
    public ClubDeletion(Club club, boolean isDeleted) {
        this.club = club;
        this.isDeleted = false;
    }

    public void deleteClub() {
        isDeleted = true;
    }
}
