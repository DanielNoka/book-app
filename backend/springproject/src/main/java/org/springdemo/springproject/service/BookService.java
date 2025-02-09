package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.BookDTO;
import org.springdemo.springproject.entity.Book;

import java.util.List;

public interface BookService {

    List<BookDTO> getAll();
    BookDTO getById(Long id);
    BookDTO createBook(BookDTO book);
    BookDTO updateBook(Long id, BookDTO book);
    void deleteBook(Long id);

    BookDTO addAuthorToBook(Long bookId, Long authorId);
    BookDTO removeAuthorFromBook(Long authorId, Long bookId);
}
