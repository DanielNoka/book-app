package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.BookCreateDto;
import org.springdemo.springproject.dto.BookUpdateDto;
import org.springdemo.springproject.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAll();
    Book getById(Long id);
    Book createBook(BookCreateDto book);
    Book updateBook(Long id, BookUpdateDto book);
    void deleteBook(Long id);

}
