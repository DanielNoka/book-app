package org.springdemo.springproject.controller;

import org.springdemo.springproject.dto.BookCreateDto;
import org.springdemo.springproject.dto.BookUpdateDto;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.exception.BookNotFoundException;
import org.springdemo.springproject.service.BookService;
import org.springdemo.springproject.util.ApiResponse;
import org.springdemo.springproject.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static org.springdemo.springproject.util.Constants.FAIL;
import static org.springdemo.springproject.util.Constants.OK;


//@CrossOrigin(origins = "http://localhost:3000") //lejon thirrjet nga react
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/{id}")
    public ApiResponse<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getById(id);
        return ApiResponse.map(book, OK, HttpStatus.OK);
    }


    @PostMapping
    public ApiResponse<Book> saveBook(@RequestBody BookCreateDto book) {
        Book createdBook = bookService.createBook(book);
        return  ApiResponse.map(createdBook, "Book created", HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ApiResponse<Book> updateBook(@PathVariable Long id, @RequestBody BookUpdateDto book) {
        Book updatedBook = bookService.updateBook(id, book);
        return  ApiResponse.map(updatedBook, "Book updated", HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ApiResponse<HttpStatus> deleteBook(@PathVariable Long id) {
        Book book = bookService.getById(id); //used to return a exception if the book is not in database
        bookService.deleteBook(id);
        return  ApiResponse.map(null, "Book deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ApiResponse<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAll();
        return  ApiResponse.map(books, "List of books", HttpStatus.OK);

    }



}