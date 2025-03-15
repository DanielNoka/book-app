package org.springdemo.springproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.dto.AuthorDTO;
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
@Tag(name = "Author", description = "API for managing Authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{authorId}/books")
    @Operation(summary = "Retrieve author books", description = "Retrieve all books associated with a author.")
    public ApiResponse<List<Book>> getBooksByAuthor(@PathVariable Long authorId) {
        List<Book> books = authorService.getBooksByAuthorId(authorId);
        return ApiResponse.map(books,OK, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fetch a author", description = "Retrieve a author by its ID")
    public ApiResponse<Author> getAuthorById(@PathVariable Long id) {
        Author author= authorService.getById(id);
        return ApiResponse.map(author, OK, HttpStatus.OK);
    }

    @GetMapping("/nationality/{nationality}")
    @Operation(summary = "Fetch authors by nationality", description = "Retrieve all authors of a specific nationality.")
    public ApiResponse<List<Author>> getAuthorsByNationality(@PathVariable String nationality) {
        List<Author> authors = authorService.getAuthorsByNationality(nationality);
        return ApiResponse.map(authors,OK, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create an author", description = "Create a new author with an auto-generated ID.")
    public ApiResponse<Author> createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        Author newAuthor = authorService.createAuthor(authorDTO);
        return  ApiResponse.map(newAuthor, CREATED, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an author", description = "Updates a existing author by its ID.")
    public ApiResponse<Author> updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorDTO authorDTO) {
        Author updatedAuthor = authorService.updateAuthor(id, authorDTO);
        return  ApiResponse.map(updatedAuthor, UPDATED, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author", description = "Delete a author by its ID")
    public ApiResponse<HttpStatus> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return  ApiResponse.map(null, OK, HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Retrieve all authors", description = "Retrieve all authors in the system.")
    public ApiResponse<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAll();
        return  ApiResponse.map(authors, OK, HttpStatus.OK);
    }

}

