package com.ra.service.category;

import com.ra.model.dto.request.CategoryRequest;
import com.ra.model.dto.response.CategoryResponse;
import com.ra.model.entity.Category;
import com.ra.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryResponse::new).toList();
    }

    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) {
        return null;
    }

    @Override
    public CategoryResponse findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
