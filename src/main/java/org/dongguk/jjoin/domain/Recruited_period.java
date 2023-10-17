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
@Table(name = "recruited_periods")
public class Recruited_period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @Builder
    public Recruited_period(Club club, Timestamp startDate, Timestamp endDate, Boolean isFinished) {
        this.club = club;
        this.startDate = startDate;
        this.endDate = endDate;
        // isFinished가 주어지지 않았을 때
        if (this.startDate.after(Timestamp.valueOf(LocalDateTime.now())) && this.endDate.before(Timestamp.valueOf(LocalDateTime.now())))
            this.isFinished = true;
        else this.isFinished = false;
        this.isFinished = isFinished;
    }
}