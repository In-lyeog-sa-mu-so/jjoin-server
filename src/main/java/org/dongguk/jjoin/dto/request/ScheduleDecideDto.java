package org.dongguk.jjoin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDecideDto {
    private Long scheduleId;
    private Boolean acceptCheck;
}
