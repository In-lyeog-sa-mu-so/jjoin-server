package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dongguk.jjoin.domain.type.DependentType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "leader_id", nullable = false)
    private Long leaderId;

    @Column(name = "dependent", nullable = false)
    @Enumerated(EnumType.STRING)
    private DependentType dependent;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "group_profile")
    private Long groupProfile;

    @Column(name = "background_image")
    private Long backgroundImage;

    //--------------------------------------------------------
    @OneToMany(mappedBy = "Group_tag", fetch = FetchType.LAZY)
    List<Group_tag> groupTags = new ArrayList<>();

    @OneToMany(mappedBy = "Group_member", fetch = FetchType.LAZY)
    List<Group_member> group_members = new ArrayList<>();

    @Builder
    public Group(String name, String introduction, Long leaderId, DependentType dependent, Long groupProfile, Long backgroundImage) {
        this.name = name;
        this.introduction = introduction;
        this.leaderId = leaderId;
        this.dependent = dependent;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());;
        this.groupProfile = groupProfile;
        this.backgroundImage = backgroundImage;
    }
}
