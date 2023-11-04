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
@Table(name = "application_answers")
public class Application_answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_question_id", nullable = false)
    private Application_question applicationQuestion;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Application_answer(Application_question applicationQuestion, String content) {
        this.applicationQuestion = applicationQuestion;
        this.content = content;
    }
}