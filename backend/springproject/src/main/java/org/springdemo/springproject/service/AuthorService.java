package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.AuthorDTO;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import java.util.List;

public interface AuthorService {

    List<Author> getAll();
    Author getById(Long id);
    List<Author> getAuthorsByNationality(String nationality);
    Author createAuthor(AuthorDTO authorDTO);
    Author updateAuthor(Long id, AuthorDTO authorDTO);
    void deleteAuthor(Long id);
    List<Book> getBooksByAuthorId(Long authorId);

}
