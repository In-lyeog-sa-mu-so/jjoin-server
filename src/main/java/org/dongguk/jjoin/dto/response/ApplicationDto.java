package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ApplicationDto {
    private Long id;
    private String name;
    private Long studentId;
    private String major;
    private String email;
    private Timestamp requestDate;

    @Builder
    public ApplicationDto(Long id, String name, Long studentId, String major, String email, Timestamp requestDate) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.major = major;
        this.email = email;
        this.requestDate = requestDate;
    }
}
