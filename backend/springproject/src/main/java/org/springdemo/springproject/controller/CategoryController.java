package org.springdemo.springproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.dto.CategoryDTO;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.Category;
import org.springdemo.springproject.service.BookService;
import org.springdemo.springproject.service.CategoryService;
import org.springdemo.springproject.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springdemo.springproject.util.Constants.OK;
import static org.springdemo.springproject.util.Constants.CREATED;
import static org.springdemo.springproject.util.Constants.UPDATED;


@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/books/{categoryId}")
    public ApiResponse<List<Book>> getBooksByCategoryId(@PathVariable Long categoryId) {
        List<Book> books = categoryService.getBooksByCategoryId(categoryId);
        return  ApiResponse.map(books,OK,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ApiResponse<List<Category>> getCategoriesByName(@RequestParam String name) {
        List<Category> categories = categoryService.searchCategoriesByName(name);
        return ApiResponse.map(categories,OK,HttpStatus.OK);
    }

    @GetMapping
    public ApiResponse<List<Category>> getAll(){
        List<Category> categories =  categoryService.getAll();
        return ApiResponse.map(categories,OK,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ApiResponse<Category> getCategoryById(@PathVariable Long categoryId){
        Category category = categoryService.getById(categoryId);
        return  ApiResponse.map(category,OK, HttpStatus.OK);
    }

    @PostMapping
    public ApiResponse<Category> createCategory(@RequestBody @Valid CategoryDTO createCategoryDTO){
        Category category = categoryService.createCategory(createCategoryDTO);
        return  ApiResponse.map(category,CREATED, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ApiResponse<Category> updateCategory(@PathVariable Long categoryId, @Valid  @RequestBody CategoryDTO createCategoryDTO){
        Category updatedCategory = categoryService.updateCategory(categoryId,createCategoryDTO);
        return  ApiResponse.map(updatedCategory,UPDATED, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ApiResponse<HttpStatus> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return ApiResponse.map(null,OK, HttpStatus.NO_CONTENT);
    }

}
