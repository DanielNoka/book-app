package org.springdemo.springproject.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdemo.springproject.dto.CreateBookDTO;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.service.BookAuthorService;
import org.springdemo.springproject.service.BookCategoryService;
import org.springdemo.springproject.service.BookService;
import org.springdemo.springproject.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springdemo.springproject.util.Constants.OK;
import static org.springdemo.springproject.util.Constants.CREATED;
import static org.springdemo.springproject.util.Constants.UPDATED;

@AllArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

    BookService bookService;
    BookAuthorService bookAuthorService;
    BookCategoryService bookCategoryService;


    @PostMapping({"{bookId}/toCategory/{categoryId}"})
    public ApiResponse<HttpStatus> addBookToCategory(@PathVariable Long bookId, @PathVariable Long categoryId) {
        bookCategoryService.addBookToCategory(bookId, categoryId);
        return ApiResponse.map(null,OK,HttpStatus.OK);
    }

    @PostMapping("/{bookId}/authors/{authorId}")
    public ApiResponse<HttpStatus> addAuthorToBook(@PathVariable Long bookId, @PathVariable Long authorId) {
        bookAuthorService.addAuthorToBook(bookId, authorId);
        return ApiResponse.map(null,OK,HttpStatus.OK);
    }

    @GetMapping("/authors/{bookId}")
    public ApiResponse<List<Author>> getBooksByAuthorId(@PathVariable Long bookId) {
        List<Author> books = bookService.getAuthorsByBookId(bookId);
        return ApiResponse.map(books,OK,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ApiResponse<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getById(id);
        return ApiResponse.map(book, OK, HttpStatus.OK);
    }

    @PostMapping
    public ApiResponse<Book> saveBook(@RequestBody @Valid CreateBookDTO createBookDTO) {
        Book createdBook = bookService.createBook(createBookDTO);
        return  ApiResponse.map(createdBook, CREATED, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ApiResponse<Book> updateBook(@PathVariable Long id, @RequestBody @Valid CreateBookDTO createBookDTO) {
        Book updatedBook = bookService.updateBook(id, createBookDTO);
        return  ApiResponse.map(updatedBook, UPDATED, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<HttpStatus> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return  ApiResponse.map(null, OK, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ApiResponse<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAll();
        return  ApiResponse.map(books, OK, HttpStatus.OK);
    }

}