package com.alkemy.ong.service;

import com.alkemy.ong.models.request.CategoryRequest;
import com.alkemy.ong.models.response.CategoryNameResponse;
import com.alkemy.ong.models.response.CategoryPageResponse;
import com.alkemy.ong.models.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryNameResponse> getCategories();
    CategoryPageResponse getCategoryPage(Integer page);
    CategoryResponse getCategoryDetail(Long id);
    CategoryResponse createCategory(CategoryRequest category);
    CategoryResponse updateCategory(Long id, CategoryRequest category);
    void deleteCategory(Long id);
}
