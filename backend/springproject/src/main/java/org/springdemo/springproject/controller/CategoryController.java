package org.springdemo.springproject.controller;

import co.elastic.clients.elasticsearch.nodes.Http;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdemo.springproject.dto.CategoryDTO;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.BookCategory;
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

@AllArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    CategoryService categoryService;
    BookService bookService;

    @GetMapping("/books/{categoryId}")
    public ApiResponse<List<Book>> getBooksByCategoryId(@PathVariable Long categoryId) {
        List<Book> books = bookService.getBooksByCategoryId(categoryId);
        return  ApiResponse.map(books,OK,HttpStatus.OK);
    }

    @GetMapping("/all")
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
