package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.CategoryRequest;
import com.alkemy.ong.models.response.CategoryResponse;
import com.alkemy.ong.service.CategoryService;
import io.swagger.annotations.*;
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
@Api(value = "Category Controller", description = "CRUD methods pertaining to Categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @ApiOperation(value = "Get all categories", notes = "Return a list of categories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Page not found")
    })
    public ResponseEntity<?> getAllCategories(@RequestParam @ApiParam(name = "page", type = "Integer", value = "Page of category", example = "1", required = false) Optional<Integer> page){
        if (page.isPresent()){
            return new ResponseEntity<>(categoryService.getCategoryPage(page.get()), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a category by id", notes = "Return a category as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Category not found")
    })
    public ResponseEntity<CategoryResponse> getCategoryDetail(@PathVariable @ApiParam(name = "id",type = "Long", value = "Category id", example = "1", required = true) Long id){
        return new ResponseEntity<>(categoryService.getCategoryDetail(id), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Create a category", notes = "Create and return a category")
    @ApiResponse(code = 201, message = "CREATED")
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody @ApiParam(name = "Category category", value = "Category category", required = true) CategoryRequest category){
        return new ResponseEntity<>(categoryService.createCategory(category),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Update a category by id", notes = "Update and return a category as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Category not found")
    })
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable @ApiParam(name = "id", type = "Long", value = "Category id", example = "1", required = true) Long id, @RequestBody @Valid @ApiParam(name = "Category category", value = "Category category", required = true) CategoryRequest category){
        return new ResponseEntity<>(categoryService.updateCategory(id, category), HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    @ApiOperation(value = "Delete a category by id", notes = "Delete and return a category as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Category not found")
    })
    public ResponseEntity<?> deleteCategory(@PathVariable @ApiParam(name = "id", type = "Long", value = "Category id", example = "1", required = true) Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
