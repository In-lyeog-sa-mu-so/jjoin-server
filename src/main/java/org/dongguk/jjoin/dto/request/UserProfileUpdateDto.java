package org.dongguk.jjoin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateDto {
    private String profileImageUuid;
    private String introduction;
}
