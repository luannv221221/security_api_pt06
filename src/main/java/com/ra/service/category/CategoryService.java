package com.ra.service.category;

import com.ra.model.dto.request.CategoryRequest;
import com.ra.model.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();
    CategoryResponse save(CategoryRequest categoryRequest);
    CategoryResponse findById(Long id);
    void delete(Long id);
}
