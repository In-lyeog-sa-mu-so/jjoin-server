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
@Table(name = "clubs")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "club_name", nullable = false)
    private String name;

    @Column(name = "introduction")
    private String introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    private User leader;

    @Column(name = "dependent", nullable = false)
    @Enumerated(EnumType.STRING)
    private DependentType dependent;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "club_image")
    private Image clubImage;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "background_image")
    private Image backgroundImage;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    //--------------------------------------------------------

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<ClubTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    List<ClubMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    List<Plan> plans = new ArrayList<>();

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    List<ClubApplication> clubApplications = new ArrayList<>();

    @OneToMany(mappedBy = "club", fetch = FetchType.LAZY)
    List<Application_question> applicationQuestions = new ArrayList<>();

    @Builder
    public Club(String name, String introduction, User leader, DependentType dependent, Image clubImage, Image backgroundImage) {
        this.name = name;
        this.introduction = introduction;
        this.leader = leader;
        this.dependent = dependent;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());;
        this.clubImage = clubImage;
        this.backgroundImage = backgroundImage;
    }

    // 동아리 개설
    public void enrollClub() {
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
    }

    public void modifyClubText(String intorudction) {
        this.introduction = introduction;
    }

    public void modifyClubIntorudction(String introduction) {
        this.introduction = introduction;
    }

    public void modifyClubImage(Image clubImage) {
        this.clubImage = clubImage;
    }

    public void modifyBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void deleteClub() {
        this.isDeleted = Boolean.TRUE;
    }
}
