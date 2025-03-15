package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.BookDTO;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {


    Page<Book> findAll(Pageable pageable);
    Book getById(Long id);
    Book createBook(BookDTO bookDTO);
    Book updateBook(Long id, BookDTO bookDTO);
    void deleteBook(Long id);
    List<Author> getAuthorsByBookId(Long bookId);
    List<Book> findByYear(Integer year);

    //Bulk operation
    List<Book> createBooks(List<BookDTO> bookDTOS);
}
