package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class ClubEnrollmentDto {
    private Long id;
    private String clubName;
    private String dependent;
    private List<String> tags;
    private Timestamp createdDate;

    @Builder
    public ClubEnrollmentDto(Long id, String clubName, String dependent, List<String> tags, Timestamp createdDate) {
        this.id = id;
        this.clubName = clubName;
        this.dependent = dependent;
        this.tags = tags;
        this.createdDate = createdDate;
    }
}
