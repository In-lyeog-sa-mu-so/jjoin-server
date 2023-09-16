package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "recruited_period")
public class Recruited_period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @Column(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;

    @Builder
    public Recruited_period(Group group, Timestamp startDate, Timestamp endDate) {
        this.group = group;
        this.startDate = startDate;
        this.endDate = endDate;
        isFinished = false;
    }
}