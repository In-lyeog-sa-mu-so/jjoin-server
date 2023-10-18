package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dongguk.jjoin.dto.request.PlanUpdateDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date", nullable = false)
    private Timestamp updatedDate;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    //--------------------------------------------------------

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Schedule> schedules = new ArrayList<>();

    @Builder
    public Plan(Club club, Timestamp startDate, Timestamp endDate, String title, String content) {
        this.club = club;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
        this.updatedDate = Timestamp.valueOf(LocalDateTime.now());
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
    }

    public void updatePlan(PlanUpdateDto planUpdateDto) {
        this.title = planUpdateDto.getTitle();
        this.content = planUpdateDto.getContent();
        this.startDate = planUpdateDto.getStartDate();
        this.endDate = planUpdateDto.getEndDate();
        this.updatedDate = Timestamp.valueOf(LocalDateTime.now());
    }
}
