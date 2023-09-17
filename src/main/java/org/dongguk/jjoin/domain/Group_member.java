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
@Table(name = "group_member")
public class Group_member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "rank", nullable = false)
    @Enumerated(EnumType.STRING)
    private RankType rankType;

    @Column(name = "register_date", nullable = false)
    private Timestamp registerDate;

    @Builder
    public Group_member(User user, Group group, RankType rankType) {
        this.user = user;
        this.group = group;
        this.rankType = rankType;
        registerDate = Timestamp.valueOf(LocalDateTime.now());
    }
}
