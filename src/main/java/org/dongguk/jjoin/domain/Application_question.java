package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "application_questions")
public class Application_question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Application_question(Club club, String content) {
        this.club = club;
        this.content = content;
    }
    //-------------------
    @OneToMany(mappedBy = "applicationQuestion", fetch = FetchType.LAZY)
    List<Application_answer> applicationAnswers = new ArrayList<>();

    public void modifyContent(String content){ this.content = content; }
}
