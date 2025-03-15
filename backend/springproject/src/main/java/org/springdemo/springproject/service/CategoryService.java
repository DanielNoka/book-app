package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.CategoryDTO;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CategoryService {

    List<Category> getAll();
    Category getById(Long categoryId);
    Category createCategory(CategoryDTO createCategoryDTO);
    Category updateCategory(Long categoryId, CategoryDTO createCategoryDTO);
    void deleteCategory(Long id);
    List<Book> getBooksByCategoryId(Long categoryId);
    List<Book> getBooksByCategoryName(String categoryName);

    Page<CategoryDTO> searchCategories(String categoryName, String categoryDescription, Pageable pageable);
}
