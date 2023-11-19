package org.dongguk.jjoin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubMainPageUpdateDto {
    private String introduction;
    private Timestamp startDate;
    private Timestamp endDate;
}
