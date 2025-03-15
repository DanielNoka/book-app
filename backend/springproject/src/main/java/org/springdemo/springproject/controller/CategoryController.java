package org.springdemo.springproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.dto.CategoryDTO;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.Category;
import org.springdemo.springproject.service.CategoryService;
import org.springdemo.springproject.util.ApiResponse;
import org.springdemo.springproject.util.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springdemo.springproject.util.Constants.OK;
import static org.springdemo.springproject.util.Constants.CREATED;
import static org.springdemo.springproject.util.Constants.UPDATED;
import  static  org.springdemo.springproject.util.Constants.PAGE_SIZE;



@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
@Tag(name = "Category", description = "API for managing Categories.")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/books/id/{categoryId}")
    @Operation(summary = "Retrieve Books by category ID", description = "Fetches books associated with category by category ID")
    public ApiResponse<List<Book>> getBooksByCategoryId(@PathVariable Long categoryId) {
        List<Book> books = categoryService.getBooksByCategoryId(categoryId);
        return ApiResponse.map(books,OK,HttpStatus.OK);
    }

    @GetMapping("/books/name/{categoryName}")
    @Operation(summary = "Retrieve Books by category name", description = "Fetches books associated with category by category Name")
    public ApiResponse<List<Book>> getBooksByCategoryName(@PathVariable String categoryName) {
        List<Book> books = categoryService.getBooksByCategoryName(categoryName);
        return ApiResponse.map(books,OK,HttpStatus.OK);
    }

    // Pagination && Sorting
    @GetMapping
    @Operation(summary = "Retrieve Categories", description = "Fetches a paginated and sortable list of categories. Defaults: 3 items per page, sorted by ID in ascending order.")
    public ApiResponse<PaginatedResponse<CategoryDTO>> searchCategories(
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String categoryDescription,
            @PageableDefault(size = PAGE_SIZE, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<CategoryDTO> categories = categoryService.searchCategories( categoryName, categoryDescription, pageable);
        return ApiResponse.map(new PaginatedResponse<>(categories), OK, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "Retrieve a Category", description = "Fetches a category by its ID")
    public ApiResponse<Category> getCategoryById(@PathVariable Long categoryId){
        Category category = categoryService.getById(categoryId);
        return  ApiResponse.map(category,OK, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a Category", description = "Creates a new category. ID is auto-generated.")
    public ApiResponse<Category> createCategory(@RequestBody @Valid CategoryDTO createCategoryDTO){
        Category category = categoryService.createCategory(createCategoryDTO);
        return  ApiResponse.map(category,CREATED, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "Update a Category", description = "Updates an existing category by its ID.")
    public ApiResponse<Category> updateCategory(@PathVariable Long categoryId, @Valid  @RequestBody CategoryDTO createCategoryDTO){
        Category updatedCategory = categoryService.updateCategory(categoryId,createCategoryDTO);
        return  ApiResponse.map(updatedCategory,UPDATED, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Delete a Category", description = "Deletes a category by its ID.")
    public ApiResponse<HttpStatus> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ApiResponse.map(null,OK, HttpStatus.NO_CONTENT);
    }

}
