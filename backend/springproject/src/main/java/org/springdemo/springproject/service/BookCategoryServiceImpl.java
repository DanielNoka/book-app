package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.BookCategory;
import org.springdemo.springproject.entity.Category;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.BookCategoryRepository;
import org.springdemo.springproject.repository.BookRepository;
import org.springdemo.springproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookCategoryServiceImpl implements BookCategoryService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;

    @Override
    @Transactional
    public void addBookToCategory(Long bookId, Long categoryId) {
        log.info("Adding Book with ID {} to Category with ID {}", bookId, categoryId);
        Book book = bookRepository.findById(bookId).
                orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));

        if(bookCategoryRepository.existsByBookIdAndCategoryId(bookId, categoryId)) {
            throw new EntityNotFoundException("This relationship already exists");
        }

        BookCategory bookCategory = new BookCategory();
        bookCategory.setBook(book);
        bookCategory.setCategory(category);

        bookCategoryRepository.save(bookCategory);
        log.info("Book with ID {} added successfully to Category ID {} & name {}", bookId ,  categoryId , category.getCategoryName());
    }
}
