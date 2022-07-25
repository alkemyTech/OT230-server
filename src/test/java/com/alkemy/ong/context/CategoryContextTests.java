package com.alkemy.ong.context;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.entity.CategoryEntity;
import com.alkemy.ong.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class CategoryContextTests extends ContextTests {

    @Autowired
    private CategoryRepository categoryRepository;


    protected CategoryEntity createCategory() throws Exception{
            CategoryEntity category =
                    buildCategory("Category Test", "Description", "url_image");
            categoryRepository.save(category);
            return category;
    }
    private CategoryEntity buildCategory(String name, String description, String image) {
        return CategoryEntity.builder()
                .name(name)
                .description(description)
                .image(image)
                .build();
    }


}
