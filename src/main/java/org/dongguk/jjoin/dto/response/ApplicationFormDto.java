package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class ApplicationFormDto {
    private String clubName;
    private Timestamp startDate;
    private Timestamp endDate;
    private List<ApplicationQuestionResponseDto> applicationQuestionResponseDtos;

    @Builder
    public ApplicationFormDto(String clubName, Timestamp startDate, Timestamp endDate, List<ApplicationQuestionResponseDto> applicationQuestionResponseDtos) {
        this.clubName = clubName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.applicationQuestionResponseDtos = applicationQuestionResponseDtos;
    }
}
