package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dongguk.jjoin.dto.request.NoticeRequestDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "notices")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private Club club;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    //--------------------------

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY)
    List<Image> notices = new ArrayList<>();

    @Builder
    public Notice(String title, boolean isPrivate, String content, Club club) {
        this.title = title;
        this.isPrivate = isPrivate;
        this.content = content;
        this.club = club;
        this.createdDate = this.updatedDate = Timestamp.valueOf(LocalDateTime.now());
        this.isDeleted = false;
    }

    public void updateNotice(NoticeRequestDto noticeRequestDto){
        this.title = noticeRequestDto.getTitle();
        this.content = noticeRequestDto.getContent();
        this.updatedDate = Timestamp.valueOf(LocalDateTime.now());
    }

    public void deleteNotice(){
        this.isDeleted = true;
    }
}
