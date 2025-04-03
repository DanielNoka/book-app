package org.springdemo.springproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.springproject.dto.BookDTO;
import org.springdemo.springproject.dto.ReviewDTO;
import org.springdemo.springproject.dto.ReviewResponseDTO;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.service.*;
import org.springdemo.springproject.util.ApiResponse;
import org.springdemo.springproject.util.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springdemo.springproject.util.Constants.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
@Tag(name = "Book", description = "API for managing Books ")
@Slf4j
public class BookController {

    private final BookService bookService;
    private final BookAuthorService bookAuthorService;
    private final BookCategoryService bookCategoryService;
    private final ReviewService reviewService;

    @PostMapping({"{bookId}/categories/{categoryId}"})
    @Operation(summary = "Add book to a category", description = "Assigns a book to a specific category.")
    public ApiResponse<HttpStatus> addBookToCategory(@PathVariable Long bookId, @PathVariable Long categoryId) {
        bookCategoryService.addBookToCategory(bookId, categoryId);
        return ApiResponse.map(null, OK, HttpStatus.OK);
    }

    @PostMapping("/{bookId}/authors/{authorId}")
    @Operation(summary = "Add author to a book", description = "Associates an author with a book.")
    public ApiResponse<HttpStatus> addAuthorToBook(@PathVariable Long bookId, @PathVariable Long authorId) {
        bookAuthorService.addAuthorToBook(bookId, authorId);
        return ApiResponse.map(null, OK, HttpStatus.OK);
    }

    @GetMapping("/{bookId}/authors")
    @Operation(summary = "Retrieve book authors", description = "Fetches authors associated with a given book ID.")
    public ApiResponse<List<Author>> getAuthorsByBookId(@PathVariable Long bookId) {
        List<Author> books = bookService.getAuthorsByBookId(bookId);
        return ApiResponse.map(books, OK, HttpStatus.OK);
    }

    @GetMapping("/year/{year}")
    @Operation(summary = "Retrieve books by year", description = "Fetches books published in a specific year.")
    public ApiResponse<List<Book>> getBooksByYear(@PathVariable Integer year) {
        List<Book> books = bookService.findByYear(year);
        return ApiResponse.map(books, OK, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fetch a book", description = "Retrieves a book by its ID")
    public ApiResponse<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getById(id);
        return ApiResponse.map(book, OK, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a book", description = "Create a new book with an auto-generated ID")
    public ApiResponse<Book> createBook(@RequestBody @Valid BookDTO bookDTO) {
        Book createdBook = bookService.createBook(bookDTO);
        return ApiResponse.map(createdBook, CREATED, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Bulk insert books", description = "Insert multiple books once")
    public ApiResponse<List<Book>> createBooks(@RequestBody @Valid List<BookDTO> bookDTOS) {
        List<Book> savedBooks = bookService.createBooks(bookDTOS);
        return ApiResponse.map(savedBooks, OK, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Updates a existing book by its ID")
    public ApiResponse<Book> updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO) {
        Book updatedBook = bookService.updateBook(id, bookDTO);
        return ApiResponse.map(updatedBook, UPDATED, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Delete a book by its ID")
    public ApiResponse<HttpStatus> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ApiResponse.map(null, OK, HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Retrieve books", description = "Fetches books with pagination support.")
    public ApiResponse<PaginatedResponse<Book>> getAll(
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Book> books = bookService.findAll(pageable);
        return ApiResponse.map(new PaginatedResponse<>(books), OK, HttpStatus.OK);
    }

    @PostMapping("{bookId}/reviews")
    public ApiResponse<ReviewResponseDTO> addReview(@PathVariable Long bookId, @RequestBody ReviewDTO reviewDTO) {
        ReviewResponseDTO review =  reviewService.addReview(bookId, reviewDTO);
        return ApiResponse.map(review, OK, HttpStatus.OK);
    }

    @GetMapping("/{bookId}/reviews")
    public ApiResponse<List<ReviewResponseDTO>> getReviewsByBookId(@PathVariable Long bookId) {
        List<ReviewResponseDTO> reviews = reviewService.findReviewsByBookId(bookId);
        return ApiResponse.map(reviews, OK, HttpStatus.OK);
    }
}