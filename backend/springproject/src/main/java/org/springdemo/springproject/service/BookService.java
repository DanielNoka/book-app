package org.springdemo.springproject.service;


import org.springdemo.springproject.dto.BookDTO;
import org.springdemo.springproject.dto.CreateBookDTO;
import org.springdemo.springproject.entity.Book;

import java.util.List;

public interface BookService {

    List<BookDTO> getAll();
    BookDTO getById(Long id);
    BookDTO createBook(CreateBookDTO createBookDTO);
    BookDTO updateBook(Long id, CreateBookDTO createBookDTO);
    void deleteBook(Long id);


}
