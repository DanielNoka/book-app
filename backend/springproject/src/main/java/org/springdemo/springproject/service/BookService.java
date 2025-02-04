package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.BookDto;
import org.springdemo.springproject.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();
    Book getById(Long id);
    Book createBook(BookDto book);
    Book updateBook(Long id, BookDto book);
    void deleteBook(Long id);

}
