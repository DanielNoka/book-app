package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springdemo.springproject.dto.CategoryDTO;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.BookCategory;
import org.springdemo.springproject.entity.Category;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with ID " + categoryId + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAll() {
        log.info("Fetching all categories...");
        List<Category> categories =  categoryRepository.findAll();
        log.info("Found {} categories", categories.size());
        return categories;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByCategoryId(Long categoryId) {
        log.info("Fetching books for category ID: {}...", categoryId);
        Category category = findCategoryById(categoryId);

        List<Book> books = category.getBookCategories().stream()
                .map(BookCategory::getBook)
                .collect(Collectors.toList());
        log.info("Found {} books with category ID: {}", books.size(), categoryId);
        return books;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByCategoryName(String categoryName) {
        Category category = categoryRepository.findCategoriesByCategoryName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Category with name '" + categoryName + "' has no related books or doesnt exist"));
        List<Book> books = category.getBookCategories().stream()
                .map(BookCategory::getBook)
                .collect(Collectors.toList());
        log.info("Found {} books with category Name: {}", books.size(), categoryName);
        return books;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> searchCategories(String categoryName, String categoryDescription, Pageable pageable) {
        Page<Category> categories = categoryRepository.searchCategories(categoryName, categoryDescription, pageable);
        return categories.map(category -> modelMapper.map(category, CategoryDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Category getById(Long categoryId) {
        log.info("Fetching category with ID {}...", categoryId);
        Category category =  findCategoryById(categoryId);
        log.info("Category with ID {} fetched successfully", categoryId);
        return category;
    }

    @Override
    @Transactional
    public Category createCategory(CategoryDTO createCategoryDTO) {
        log.info("Creating a new category...");
        Category newCategory = modelMapper.map(createCategoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(newCategory);
        log.info("Category created successfully with ID {}", savedCategory.getId());
        return  savedCategory;
    }

    @Override
    @Transactional
    public Category updateCategory(Long categoryId, CategoryDTO createCategoryDTO) {

        log.info("Updating category with ID {}...", categoryId);
        Category existingCategory = findCategoryById(categoryId);
        modelMapper.map(createCategoryDTO, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);
        log.info("Category with ID {} updated successfully", categoryId);
        return updatedCategory;
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        log.info("Deleting category with ID {}...", id);
        Category category = findCategoryById(id);
        categoryRepository.delete(category);
        log.info("Category with ID {} deleted successfully", id);
    }

}
