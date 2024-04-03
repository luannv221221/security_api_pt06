package com.ra.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryRequest {
    private Long id;
    private String categoryName;
    private String description;
    private boolean status;
}
