package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.CreateBookDTO;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    List<Book> getAll();
    Book getById(Long id);
    Book createBook(CreateBookDTO createBookDTO);
    Book updateBook(Long id, CreateBookDTO createBookDTO);
    void deleteBook(Long id);
    List<Author> getAuthorsByBookId(Long bookId);
    List<Book> getBooksByCategoryId(Long categoryId);
}
