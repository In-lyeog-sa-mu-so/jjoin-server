package org.dongguk.jjoin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApplicationQAset {
    private String question;
    private String answer;

    @Builder
    public ApplicationQAset(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
}
