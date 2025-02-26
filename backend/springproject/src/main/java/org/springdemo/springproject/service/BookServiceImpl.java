package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public List<Author> getAuthorsByBookId(Long bookId) {
        log.info("Fetching authors by book id {}", bookId);
        List<Author> authors = bookRepository.findAuthorsByBookId(bookId);

        if (authors.isEmpty()) {
            if (!bookRepository.existsById(bookId)) {
                throw new EntityNotFoundException("Book with ID: " + bookId + " does not exist");
            }
            throw new EntityNotFoundException("Book with ID: " + bookId + " exists but has no authors");
        }
        log.info("Found {} authors by book ID {}", authors.size(), bookId);
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
        log.info("Getting all books");
        List<Book> books = bookRepository.findAll();
        log.info("Found {} books", books.size());
        return books;
    }

    @Override
    public Book getById(Long id) {
        log.info("Getting book with ID {}", id);
        Book book =  bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + id + " not found"));
        log.info("The book with ID {} is founded", book.getId());
        return book;
    }

    @Override
    @Transactional
    public Book createBook(CreateBookDTO createBookDTO) {
        log.info("Creating book {}", createBookDTO);
        Book book = modelMapper.map(createBookDTO, Book.class);
        Book savedBook = bookRepository.save(book);
        log.info("Book created with ID :{}", savedBook.getId());
        return  savedBook;
    }

    @Override
    @Transactional
    public Book updateBook(Long id, CreateBookDTO createBookDTO) {
        log.info("Updating book with ID {}", id);
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + id + " not found, cannot update"));

        modelMapper.map(createBookDTO, existingBook);
        Book updatedBook = bookRepository.save(existingBook);
        log.info("Book with ID {} is updated", updatedBook.getId());
        return updatedBook;
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        log.info("Deleting book with ID {}", id);
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }
        bookRepository.deleteById(id);
        log.info("Book deleted with ID :{}", id);
    }
}
