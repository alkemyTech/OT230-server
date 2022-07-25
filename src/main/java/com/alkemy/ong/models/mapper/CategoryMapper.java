package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.CategoryEntity;
import com.alkemy.ong.models.request.CategoryRequest;
import com.alkemy.ong.models.response.CategoryNameResponse;
import com.alkemy.ong.models.response.CategoryPageResponse;
import com.alkemy.ong.models.response.CategoryResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryNameResponse toCategoryNameResponseList(CategoryEntity categoryEntity){
        return CategoryNameResponse.builder()
                .name(categoryEntity.getName())
                .build();
    }
    public List<CategoryNameResponse> toCategoryNameResponseList(List<CategoryEntity> categoryEntities){
        List<CategoryNameResponse> categoryNameResponseList = categoryEntities.stream()
                .map(c -> toCategoryNameResponseList(c)).collect(Collectors.toList());

        return categoryNameResponseList;
    }
    public CategoryResponse toCategoryDetailsResponse(CategoryEntity categoryEntity){
        return CategoryResponse.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .image(categoryEntity.getImage())
                .timestamp(categoryEntity.getTimestamp())
                .build();
    }

    public CategoryPageResponse toCategoryPageResponse(List<CategoryEntity> categoryEntities, String previous, String next){
        List<CategoryResponse> categoryResponseList = categoryEntities.stream()
                .map(c -> toCategoryDetailsResponse(c)).collect(Collectors.toList());
        return CategoryPageResponse.builder()
                .categories(categoryResponseList)
                .previous(previous)
                .next(next)
                .build();
    }

    public CategoryEntity toCategoryEntity(CategoryRequest category){
        return CategoryEntity.builder()
                .name(category.getName())
                .description(category.getDescription())
                .image(category.getImage())
                .build();
    }
    public CategoryEntity toUpdateCategory(CategoryEntity categoryEntity, CategoryRequest categoryRequest){
        categoryEntity.setName(categoryRequest.getName());
        categoryEntity.setDescription(categoryRequest.getDescription());
        categoryEntity.setImage(categoryRequest.getImage());
        return categoryEntity;
    }
}
