package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdemo.springproject.dto.CreateBookDTO;
import org.springdemo.springproject.entity.*;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.BookRepository;
import org.springdemo.springproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public List<Author> getAuthorsByBookId(Long bookId) {
        List<Author> authors = bookRepository.findAuthorsByBookId(bookId);

        if (authors.isEmpty()) {
            if (!bookRepository.existsById(bookId)) {
                throw new EntityNotFoundException("Book with ID: " + bookId + " does not exist");
            }
            throw new EntityNotFoundException("Book with ID: " + bookId + " exists but has no authors");
        }
        return authors;
    }

    @Override
    public List<Book> getBooksByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with ID " + categoryId + " not found"));

        return category.getBookCategories().stream()
                .map(BookCategory::getBook)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(Long id) {
        return  bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public Book createBook(CreateBookDTO createBookDTO) {

        Book newBook = modelMapper.map(createBookDTO, Book.class);

        return  bookRepository.save(newBook);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, CreateBookDTO createBookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + id + " not found, cannot update"));

        modelMapper.map(createBookDTO, existingBook);

        return bookRepository.save(existingBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
    }
}
