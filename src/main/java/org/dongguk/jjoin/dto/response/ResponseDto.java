package org.dongguk.jjoin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto<T> {
    private final Boolean success;
    private String error;
    private final T data;


    public ResponseDto(@Nullable T data) {
        this.success = true;
        this.data = data;
    }
}
