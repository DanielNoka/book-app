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

    private Book findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAuthorsByBookId(Long bookId) {
        log.info("Fetching authors by book id {}...", bookId);
        findBookById(bookId); //Ensure book exists before fetching

        List<Author> authors = bookRepository.findAuthorsByBookId(bookId);
        if (authors.isEmpty()) {
            throw new EntityNotFoundException("Book with ID: " + bookId + " exists but has no associated authors");
        }
        log.info("Found {} authors by book ID {}", authors.size(), bookId);
        return authors;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        log.info("Fetching all books...");
        List<Book> books = bookRepository.findAll();
        log.info("Found {} books", books.size());
        return books;
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(Long bookId) {
        log.info("Fetching book with ID: {}...", bookId);
        Book book = findBookById(bookId);
        log.info("Book with ID {} found", bookId);
        return book;
    }

    @Override
    @Transactional
    public Book createBook(CreateBookDTO createBookDTO) {
        log.info("Creating book with title: {}...", createBookDTO.getTitle());
        Book book = modelMapper.map(createBookDTO, Book.class);
        Book savedBook = bookRepository.save(book);
        log.info("Book created successfully with ID: {}", savedBook.getId());
        return savedBook;
    }

    @Override
    @Transactional
    public Book updateBook(Long bookId, CreateBookDTO createBookDTO) {
        log.info("Updating book with ID {}...", bookId);
        Book book = findBookById(bookId);
        modelMapper.map(createBookDTO, book);
        Book updatedBook = bookRepository.save(book);
        log.info("Successfully updated book with ID {}", bookId);
        return updatedBook;
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        log.info("Deleting book with ID: {}...", id);
        Book book = findBookById(id); // Ensures book exists before deletion
        bookRepository.delete(book);
        log.info("Deleted book with ID: {}", id);
    }

}
