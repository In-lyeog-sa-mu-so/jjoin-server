package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class ClubEnrollmentDto {
    private Long id;
    private String name;
    private String dependent;
    private List<String> tags;
    private Timestamp createdDate;

    @Builder
    public ClubEnrollmentDto(Long id, String name, String dependent, List<String> tags, Timestamp createdDate) {
        this.id = id;
        this.name = name;
        this.dependent = dependent;
        this.tags = tags;
        this.createdDate = createdDate;
    }
}
