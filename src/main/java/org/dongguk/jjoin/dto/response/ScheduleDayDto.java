package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ScheduleDayDto {
    private Long plan_id;
    private String club_name;
    private Timestamp start_date;
    private Timestamp end_date;
    private String title;
    private String content;
    private Boolean is_agreed;

    @Builder
    public ScheduleDayDto(Long plan_id, String club_name, Timestamp start_date, Timestamp end_date, String title, String content, Boolean is_agreed) {
        this.plan_id = plan_id;
        this.club_name = club_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.title = title;
        this.content = content;
        this.is_agreed = is_agreed;
    }
}
