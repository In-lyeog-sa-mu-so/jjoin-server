package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ClubScheduleDto {
    private Long plan_id;
    private Timestamp start_date;
    private Timestamp end_date;
    private String title;
    private String content;
    private Boolean is_agreed;

    @Builder
    public ClubScheduleDto(Long plan_id, Timestamp start_date, Timestamp end_date, String title, String content, Boolean is_agreed) {
        this.plan_id = plan_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.title = title;
        this.content = content;
        this.is_agreed = is_agreed;
    }
}
