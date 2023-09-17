package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date")
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

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    List<Schedule> schedules = new ArrayList<>();

    @Builder
    public Event(Group group, Timestamp startDate, Timestamp endDate, String title, String content) {
        this.group = group;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
    }
}
