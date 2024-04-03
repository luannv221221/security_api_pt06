package com.ra.model.dto.response;

import com.ra.model.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryResponse {

    private Long id;
    private String categoryName;
    private String description;
    private boolean status;
    public CategoryResponse(Category category){
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.description = category.getDescription();
        this.status = category.isStatus();
    }
}
