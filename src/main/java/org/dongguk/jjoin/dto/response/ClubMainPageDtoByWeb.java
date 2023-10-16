package org.dongguk.jjoin.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.dongguk.jjoin.domain.Image;

import java.sql.Timestamp;

@Getter
public class ClubMainPageDtoByWeb {
    private String clubImage;
    private String backgroundImage;
    private String introduction;
    private boolean isFinished;
    private Timestamp startDate;
    private Timestamp endDate;

    @Builder
    public ClubMainPageDtoByWeb(String clubImage, String backgroundImage, String introduction, boolean isFinished, Timestamp startDate, Timestamp endDate) {
        this.clubImage = clubImage;
        this.backgroundImage = backgroundImage;
        this.introduction = introduction;
        this.isFinished = isFinished;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
