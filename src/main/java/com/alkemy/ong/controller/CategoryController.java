package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.CategoryRequest;
import com.alkemy.ong.models.response.CategoryResponse;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.alkemy.ong.utils.ApiConstants.ROLE_ADMIN;

@RestController
@RequestMapping(path = "/categories")
@PreAuthorize(ROLE_ADMIN)
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories(@RequestParam Optional<Integer> page){
        if (page.isPresent()){
            return new ResponseEntity<>(categoryService.getCategoryPage(page.get()), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryDetail(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.getCategoryDetail(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest category){
        return new ResponseEntity<>(categoryService.createCategory(category),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id, @RequestBody @Valid CategoryRequest category){
        return new ResponseEntity<>(categoryService.updateCategory(id, category), HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
