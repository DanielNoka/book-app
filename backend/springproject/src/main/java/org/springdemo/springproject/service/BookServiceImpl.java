package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.BookCreateDto;
import org.springdemo.springproject.dto.BookUpdateDto;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.exception.BookNotFoundException;
import org.springdemo.springproject.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements  BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {

        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book createBook(BookCreateDto book) {
        Book newbook = new Book();
        newbook.setTitle(book.getTitle());
        newbook.setAuthor(book.getAuthor());
        newbook.setPublishYear(book.getPublishYear());
        return bookRepository.save(newbook);
    }

    public Book updateBook(Long id, BookUpdateDto book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book updatedbook = optionalBook.get();
            updatedbook.setTitle(book.getTitle());
            updatedbook.setAuthor(book.getAuthor());
            updatedbook.setPublishYear(book.getPublishYear());
            return bookRepository.save(updatedbook);
        }
        return null;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }



}

