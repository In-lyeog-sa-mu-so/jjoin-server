package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.dongguk.jjoin.domain.type.MajorType;
import org.dongguk.jjoin.domain.type.RankType;

import java.sql.Timestamp;

@Getter
public class ClubMemberDto {
    private Long userId;
    private String userName;
    private Long studentId;
    private MajorType major;
    private String email;
    private Timestamp registerDate;
    private RankType position;

    @Builder
    public ClubMemberDto(Long userId, String userName, MajorType major, String email, Long studentId, Timestamp registerDate, RankType position) {
        this.userId = userId;
        this.userName = userName;
        this.major = major;
        this.email = email;
        this.studentId = studentId;
        this.registerDate = registerDate;
        this.position = position;
    }
}
