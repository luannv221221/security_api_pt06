package com.ra.model.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDTO {
    private String fullName;
    private String userName;
    private Boolean status;
}
