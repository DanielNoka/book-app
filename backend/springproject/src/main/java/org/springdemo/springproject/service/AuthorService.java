package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.CreateAuthorDTO;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import java.util.List;

public interface AuthorService {

    List<Author> getAll();
    Author getById(Long id);
    Author createAuthor(CreateAuthorDTO createAuthorDTO);
    Author updateAuthor(Long id, CreateAuthorDTO createAuthorDTO);
    void deleteAuthor(Long id);
    List<Book> getBooksByAuthorId(Long authorId);

}
