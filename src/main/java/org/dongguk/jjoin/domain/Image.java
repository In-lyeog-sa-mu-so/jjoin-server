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

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @Column(name = "uuid_name", nullable = false)
    private String uuidName;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ImageType type;

    @Builder
    public Image(User user, Album album, Notice notice, String originName, String uuidName, ImageType type) {
        this.user = user;
        this.album = album;
        this.notice = notice;
        this.originName = originName;
        this.uuidName = uuidName;
        this.type = type;
    }
}
