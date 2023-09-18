package org.dongguk.jjoin.dto.response;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.dongguk.jjoin.domain.Club;

import java.sql.Timestamp;

@Getter
public class PlanDto {
    private Long id;
    private Long club_id;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Timestamp startDate;
    private Timestamp endDate;
    private String title;
    private String content;

    @Builder
    public PlanDto(Long id, Long club_id, Timestamp createdDate, Timestamp updatedDate, Timestamp startDate, Timestamp endDate, String title, String content) {
        this.id = id;
        this.club_id = club_id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
    }
}
