package org.springdemo.springproject.controller;

import org.springdemo.springproject.dto.BookCreateDto;
import org.springdemo.springproject.dto.BookUpdateDto;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.exception.BookNotFoundException;
import org.springdemo.springproject.service.BookService;
import org.springdemo.springproject.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//@CrossOrigin(origins = "http://localhost:3000") //lejon thirrjet nga react
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/{id}")
    public ApiResponse<?> getBookById(@PathVariable Long id) {
        try {
            Book book = bookService.getById(id);
            return new ApiResponse<>(book, "Book founded" , HttpStatus.OK);
        } catch (BookNotFoundException ex) {
            return new ApiResponse<>(null ,ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ApiResponse<Book> saveBook(@RequestBody BookCreateDto book) {
        Book createdBook = bookService.createBook(book);
        return new ApiResponse<>(createdBook, "Book created", HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ApiResponse<Book> updateBook(@PathVariable Long id, @RequestBody BookUpdateDto book) {
        Book updatedBook = bookService.updateBook(id, book);
        return new ApiResponse<>(updatedBook, "Book updated", HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ApiResponse<HttpStatus> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ApiResponse<>(null, "Book deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ApiResponse<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAll();
        return new ApiResponse<>(books, "List of books", HttpStatus.OK);

    }



}