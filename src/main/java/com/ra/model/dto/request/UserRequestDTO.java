package com.ra.model.dto.request;

import com.ra.model.entity.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRequestDTO {
    private String fullName;
    private String userName;
    private String password;
    private Boolean status;

}
