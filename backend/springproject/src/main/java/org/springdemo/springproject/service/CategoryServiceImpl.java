package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdemo.springproject.dto.CategoryDTO;
import org.springdemo.springproject.entity.Category;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> searchCategoriesByName(String categoryName) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName);
    }
    @Override
    public Category getById(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with id "+ categoryId+" not found"));
    }

    @Override
    @Transactional
    public Category createCategory(CategoryDTO createCategoryDTO) {

        Category newCategory = modelMapper.map(createCategoryDTO, Category.class);

        return  categoryRepository.save(newCategory);

    }

    @Override
    @Transactional
    public Category updateCategory(Long categoryId, CategoryDTO createCategoryDTO) {
       Category existingCategory = categoryRepository.findById(categoryId)
               .orElseThrow( () ->  new EntityNotFoundException("Category not found with id: " + categoryId ));

       modelMapper.map(createCategoryDTO, existingCategory);

       return categoryRepository.save(existingCategory);

    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

}
