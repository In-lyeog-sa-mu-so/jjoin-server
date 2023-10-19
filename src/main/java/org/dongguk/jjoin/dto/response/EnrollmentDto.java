package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class EnrollmentDto {
    private String clubName;
    private String dependent;
    private List<String> tags;
    private Timestamp createdDate;

    @Builder
    public EnrollmentDto(String clubName, String dependent, List<String> tags, Timestamp createdDate) {
        this.clubName = clubName;
        this.dependent = dependent;
        this.tags = tags;
        this.createdDate = createdDate;
    }
}
