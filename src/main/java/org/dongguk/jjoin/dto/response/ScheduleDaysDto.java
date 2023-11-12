package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
    public class ScheduleDaysDto {
        private Timestamp date;
        private List<ScheduleDayDto> scheduleDayDtos;

        @Builder
        public ScheduleDaysDto(Timestamp date, List<ScheduleDayDto> scheduleDayDtos) {
            this.date = date;
            this.scheduleDayDtos = scheduleDayDtos;
        }
}
