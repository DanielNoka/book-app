package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springdemo.springproject.dto.BookDTO;
import org.springdemo.springproject.entity.*;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.BookRepository;
import org.springdemo.springproject.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MailService mailService;

    private Book findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with ID " + bookId + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAuthorsByBookId(Long bookId) {
        log.info("Fetching authors by book id {}...", bookId);
        Book book = findBookById(bookId); //Ensure book exists before fetching

        List<Author> authors = book.getBookAuthors().stream()
                .map(BookAuthor::getAuthor)
                        .collect(Collectors.toList());
        log.info("Found {} authors by book ID {}", authors.size(), bookId);
        return authors;
    }

    @Override
    public List<Book> findByYear(Integer year) {
        if (year == null || year < 1000 || year > 9999) {
            throw new IllegalArgumentException("Year must be a 4-digit number.");
        }
        return bookRepository.findByYear(year);
    }

    @Override
    @Transactional
    public List<Book> createBooks(List<BookDTO> bookDTOS) {
        log.info("Creating books {}...", bookDTOS.size());
        List<Book> books = new ArrayList<>();
        for (BookDTO bookDTO : bookDTOS) {
            Book savedBook = createBook(bookDTO);
            books.add(savedBook);
        }
        log.info("Successfully inserted {} books", books.size());
        return books;
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        log.info("Fetching all books...");
        return bookRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(Long bookId) {
        log.info("Fetching book with ID: {}...", bookId);
        Book book = findBookById(bookId);
        log.info("Book with ID {} found", bookId);
        return book;
    }

    //send email to users
    @Override
    @Transactional
    public Book createBook(BookDTO bookDTO) {

        //used to find emails
        List<String> userEmails = userRepository.findAllEmails();
        log.info("Creating book with title: {}...", bookDTO.getTitle());
        Book book = modelMapper.map(bookDTO, Book.class);
        mailService.sendMail(userEmails, bookDTO.getTitle());
        Book savedBook = bookRepository.save(book);
        log.info("Book created successfully with ID: {}", savedBook.getId());
        return savedBook;
    }

    @Override
    @Transactional
    public Book updateBook(Long bookId, BookDTO bookDTO) {
        log.info("Updating book with ID {}...", bookId);
        Book book = findBookById(bookId);
        modelMapper.map(bookDTO, book);
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
