package com.ra.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginResponseDTO {
    private String fullName;
    private String userName;
    private String token;
}
