package org.springdemo.springproject.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdemo.springproject.dto.CategoryDTO;
import org.springdemo.springproject.entity.Category;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.CategoryRepository;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with id "+ categoryId+" not found"));
    }

    @Override
    public Category createCategory(CategoryDTO createCategoryDTO) {

        Category newCategory = modelMapper.map(createCategoryDTO, Category.class);

        return  categoryRepository.save(newCategory);

    }

    @Override
    public Category updateCategory(Long categoryId, CategoryDTO createCategoryDTO) {
       Category existingCategory = categoryRepository.findById(categoryId)
               .orElseThrow( () ->  new EntityNotFoundException("Category not found with id: " + categoryId ));

       modelMapper.map(createCategoryDTO, existingCategory);

       return categoryRepository.save(existingCategory);

    }

    @Override
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);

    }
}
