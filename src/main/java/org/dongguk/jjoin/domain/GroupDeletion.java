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
@Table(name = "group_deletion")
public class GroupDeletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Builder
    public GroupDeletion(Group group, boolean isDeleted) {
        this.group = group;
        this.isDeleted = false;
    }
}
