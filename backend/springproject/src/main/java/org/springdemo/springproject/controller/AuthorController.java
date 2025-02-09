package org.springdemo.springproject.controller;


import jakarta.validation.Valid;
import org.springdemo.springproject.dto.AuthorDTO;
import org.springdemo.springproject.service.AuthorService;
import org.springdemo.springproject.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springdemo.springproject.util.Constants.OK;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;


    @GetMapping("/{id}")
    public ApiResponse<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO author= authorService.getById(id);
        return ApiResponse.map(author, OK, HttpStatus.OK);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<AuthorDTO> saveAuthor(@RequestBody @Valid AuthorDTO authorDto) {
        AuthorDTO newAuthor = authorService.createAuthor(authorDto);
        return  ApiResponse.map(newAuthor, "Author created", HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ApiResponse<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorDTO authorDto) {
        AuthorDTO updatedAuthor = authorService.updateAuthor(id, authorDto);
        return  ApiResponse.map(updatedAuthor, "Author updated", HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ApiResponse<HttpStatus> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return  ApiResponse.map(null, "Author deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ApiResponse<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAll();
        return  ApiResponse.map(authors, "List of authors", HttpStatus.OK);

    }

}

