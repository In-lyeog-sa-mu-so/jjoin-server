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
@Table(name="tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag_name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    List<ClubTag> clubTags = new ArrayList<>();

    @Builder
    public Tag(String name) {
        this.name = name;
    }
}