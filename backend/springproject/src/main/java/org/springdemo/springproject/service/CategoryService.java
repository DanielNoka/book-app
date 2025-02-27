package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.CategoryDTO;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.Category;
import java.util.List;

public interface CategoryService {

    List<Category> getAll();
    Category getById(Long categoryId);
    Category createCategory(CategoryDTO createCategoryDTO);
    Category updateCategory(Long categoryId, CategoryDTO createCategoryDTO);
    List<Category> searchCategoriesByName(String categoryName);
    void deleteCategory(Long id);
    List<Book> getBooksByCategoryId(Long categoryId);

}
