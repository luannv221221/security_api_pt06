package com.ra.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginDTO {
    @NotBlank(message = "Khoong rong")
    private String userName;
    @Min(3)
    private String password;
}
