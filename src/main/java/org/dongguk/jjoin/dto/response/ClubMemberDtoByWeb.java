package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.dongguk.jjoin.domain.type.DependentType;
import org.dongguk.jjoin.domain.type.MajorType;
import org.dongguk.jjoin.domain.type.RankType;

import java.sql.Timestamp;

@Getter
public class ClubMemberDtoByWeb {
    private Long userId;
    private String userName;
    private Long studentId;
    private MajorType major;
    private Timestamp registerDate;
    private RankType position;

    @Builder
    public ClubMemberDtoByWeb(Long userId, String userName, MajorType major, Long studentId, Timestamp registerDate, RankType position) {
        this.userId = userId;
        this.userName = userName;
        this.major = major;
        this.studentId = studentId;
        this.registerDate = registerDate;
        this.position = position;
    }
}
