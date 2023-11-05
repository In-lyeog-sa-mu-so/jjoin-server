package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.dongguk.jjoin.domain.type.MajorType;

import java.sql.Timestamp;

@Getter
public class ApplicationDto {
    private String name;
    private Long studentId;
    private String major;
    private String email;
    private Timestamp requestDate;

    @Builder
    public ApplicationDto(String name, Long studentId, String major, String email, Timestamp requestDate) {
        this.name = name;
        this.studentId = studentId;
        this.major = major;
        this.email = email;
        this.requestDate = requestDate;
    }
}
