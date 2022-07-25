package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.OrgNotFoundException;
import com.alkemy.ong.models.entity.CategoryEntity;
import com.alkemy.ong.models.mapper.CategoryMapper;
import com.alkemy.ong.models.request.CategoryRequest;
import com.alkemy.ong.models.response.CategoryNameResponse;
import com.alkemy.ong.models.response.CategoryPageResponse;
import com.alkemy.ong.models.response.CategoryResponse;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import com.alkemy.ong.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    private static final String PATH_CATEGORY = "/categories?page=%d";

    @Override
    public List<CategoryNameResponse> getCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        List<CategoryNameResponse> categoriesResponse = categoryMapper.toCategoryNameResponseList(categories);
        return categoriesResponse;
    }

    @Override
    public CategoryPageResponse getCategoryPage(Integer page) {
        if (page < 1) {
            throw new OrgNotFoundException("Page not found");
        }

        PaginationUtil paginationUtil = new PaginationUtil(categoryRepository, page, PATH_CATEGORY);
        Page<CategoryEntity> categoriesUtil = (Page<CategoryEntity>) paginationUtil.getPage();

        return categoryMapper.toCategoryPageResponse(categoriesUtil.getContent(), paginationUtil.getPrevious(), paginationUtil.getNext());
    }

    @Override
    public CategoryResponse getCategoryDetail(Long id) {
        CategoryEntity entityDB = categoryRepository.findById(id)
                .orElseThrow(() -> new OrgNotFoundException("Category not found"));
        CategoryResponse response = categoryMapper.toCategoryDetailsResponse(entityDB);
        return response;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest category) {
        CategoryEntity categorySave = categoryMapper.toCategoryEntity(category);
        categorySave.setSoftDelete(false);
        categoryRepository.save(categorySave);
        CategoryResponse response = categoryMapper.toCategoryDetailsResponse(categorySave);
        return response;
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest category) {

        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new OrgNotFoundException("Category not found"));

        CategoryEntity categoryUpdated = categoryMapper.toUpdateCategory(categoryEntity, category);



        CategoryResponse response = categoryMapper.toCategoryDetailsResponse(categoryRepository.save(categoryUpdated));

        return response;
    }

    @Override
    public void deleteCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new OrgNotFoundException("Category not found"));
        categoryRepository.delete(categoryEntity);
    }
}
