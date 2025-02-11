package org.springdemo.springproject.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdemo.springproject.dto.BookDTO;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.service.BookAuthorService;
import org.springdemo.springproject.service.BookService;
import org.springdemo.springproject.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springdemo.springproject.util.Constants.OK;


@AllArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {


    BookService bookService;
    BookAuthorService bookAuthorService;

    //Adding author to a Book
    @PostMapping("/{bookId}/authors/{authorId}")
    public ApiResponse<HttpStatus> addAuthorToBook(@PathVariable Long bookId, @PathVariable Long authorId) {
        bookAuthorService.addAuthorToBook(bookId, authorId);
        return ApiResponse.map(null,"Author adedd to book",HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ApiResponse<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getById(id);
        return ApiResponse.map(book, OK, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<BookDTO> saveBook(@RequestBody @Valid BookDTO book) {
        BookDTO createdBook = bookService.createBook(book);
        return  ApiResponse.map(createdBook, "Book created", HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ApiResponse<BookDTO> updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO book) {
        BookDTO updatedBook = bookService.updateBook(id, book);
        return  ApiResponse.map(updatedBook, "Book updated", HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ApiResponse<HttpStatus> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return  ApiResponse.map(null, "Book deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ApiResponse<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAll();
        return  ApiResponse.map(books, "List of books", HttpStatus.OK);
    }


}