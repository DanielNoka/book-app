package org.springdemo.springproject.service;

import lombok.AllArgsConstructor;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.BookAuthor;
import org.springdemo.springproject.exception.AuthorAlreadyAddedException;
import org.springdemo.springproject.exception.AuthorNotFoundException;
import org.springdemo.springproject.exception.BookNotFoundException;
import org.springdemo.springproject.repository.AuthorRepository;
import org.springdemo.springproject.repository.BookAuthorRepository;
import org.springdemo.springproject.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BookAuthorServiceImpl implements BookAuthorService {

    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;


    @Override
    public void addAuthorToBook(Long bookId, Long authorId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id : "+bookId+" not found"));

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id : "+authorId+" not found"));



        if (bookAuthorRepository.existsByBookIdAndAuthorId(bookId,authorId)) {
            throw new AuthorAlreadyAddedException("Author with id " + authorId + " is already added to this book..");
        }

        BookAuthor bookAuthor = new BookAuthor(book, author);
        book.getBookAuthors().add(bookAuthor);
        bookRepository.save(book);


    }


}