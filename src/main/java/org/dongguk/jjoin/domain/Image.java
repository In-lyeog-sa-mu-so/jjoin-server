package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dongguk.jjoin.domain.type.ImageType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private ImageType type;

    @Builder
    public Image(User user, Album album, Notice notice, String name, ImageType type) {
        this.user = user;
        this.album = album;
        this.notice = notice;
        this.name = name;
        this.type = type;
    }
}
