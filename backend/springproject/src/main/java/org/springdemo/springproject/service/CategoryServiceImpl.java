package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springdemo.springproject.dto.CategoryDTO;
import org.springdemo.springproject.entity.Category;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Category> getAll() {
        log.info("Fetching all categories");
        List<Category> categories =  categoryRepository.findAll();
        log.info("Found {} categories", categories.size());
        return categories;
    }

    @Override
    public List<Category> searchCategoriesByName(String categoryName) {
        log.info("Fetching categories by name {}", categoryName);
        List<Category> categories = categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName);
        log.info("Found {} categories by name", categories.size());
        return categories;
    }
    @Override
    public Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.warn("Category with ID {} not found", categoryId);
                      return new EntityNotFoundException("Category with id "+ categoryId+" not found");
                });
    }

    @Override
    @Transactional
    public Category createCategory(CategoryDTO createCategoryDTO) {
        log.info("Creating a new category: {}", createCategoryDTO);
        Category newCategory = modelMapper.map(createCategoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(newCategory);
        log.info("Category created successfully with ID {}", savedCategory.getId());
        return  savedCategory;
    }

    @Override
    @Transactional
    public Category updateCategory(Long categoryId, CategoryDTO createCategoryDTO) {

        log.info("Updating category with ID {}", categoryId);
        Category existingCategory = categoryRepository.findById(categoryId)
               .orElseThrow( () -> new EntityNotFoundException("Category not found with id: " + categoryId));

       modelMapper.map(createCategoryDTO, existingCategory);
       Category updatedCategory = categoryRepository.save(existingCategory);
       log.info("Category with ID {} updated successfully", categoryId);
       return updatedCategory;

    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        log.info("Deleting category with ID {}", id);
        if(!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
        log.info("Category with ID {} deleted successfully", id);
    }

}
