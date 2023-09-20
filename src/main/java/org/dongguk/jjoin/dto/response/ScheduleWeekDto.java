package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
    public class ScheduleWeekDto {
        private Timestamp date;
        private List<ScheduleDayDto> scheduleDayDtoList;

        @Builder
        public ScheduleWeekDto(Timestamp date, List<ScheduleDayDto> scheduleDayDtoList) {
            this.date = date;
            this.scheduleDayDtoList = scheduleDayDtoList;
        }
}
