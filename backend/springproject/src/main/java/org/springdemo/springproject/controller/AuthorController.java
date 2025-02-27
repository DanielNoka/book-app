package org.springdemo.springproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.dto.CreateAuthorDTO;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.service.AuthorService;
import org.springdemo.springproject.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springdemo.springproject.util.Constants.OK;
import static org.springdemo.springproject.util.Constants.CREATED;
import static org.springdemo.springproject.util.Constants.UPDATED;


@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{authorId}/books")
    public ApiResponse<List<Book>> getBooksByAuthor(@PathVariable Long authorId) {
        List<Book> books = authorService.getBooksByAuthorId(authorId);
        return ApiResponse.map(books,OK, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ApiResponse<Author> getAuthorById(@PathVariable Long id) {
        Author author= authorService.getById(id);
        return ApiResponse.map(author, OK, HttpStatus.OK);
    }

    @PostMapping
    public ApiResponse<Author> saveAuthor(@RequestBody @Valid CreateAuthorDTO createAuthorDTO) {
        Author newAuthor = authorService.createAuthor(createAuthorDTO);
        return  ApiResponse.map(newAuthor, CREATED, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ApiResponse<Author> updateAuthor(@PathVariable Long id, @RequestBody @Valid CreateAuthorDTO createAuthorDTO) {
        Author updatedAuthor = authorService.updateAuthor(id, createAuthorDTO);
        return  ApiResponse.map(updatedAuthor, UPDATED, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<HttpStatus> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return  ApiResponse.map(null, OK, HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ApiResponse<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAll();
        return  ApiResponse.map(authors, OK, HttpStatus.OK);
    }

}

