package org.springdemo.springproject.controller;

import org.springdemo.springproject.dto.BookCreateDto;
import org.springdemo.springproject.dto.BookUpdateDto;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.exception.BookNotFoundException;
import org.springdemo.springproject.service.BookService;
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
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            Book book = bookService.getById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (BookNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody BookCreateDto book) {
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody BookUpdateDto book) {
        return new ResponseEntity<>(bookService.updateBook(id, book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAll(), HttpStatus.OK);
    }



}