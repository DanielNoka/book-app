package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.BookAuthor;
import org.springdemo.springproject.exception.AuthorAlreadyAddedException;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.AuthorRepository;
import org.springdemo.springproject.repository.BookAuthorRepository;
import org.springdemo.springproject.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookAuthorServiceImpl implements BookAuthorService {

    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public void addAuthorToBook(Long bookId, Long authorId) {

        log.info("Adding author with ID {} to Book with ID {}", authorId, bookId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id : "+bookId+" not found"));

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id : "+authorId+" not found"));

        if (bookAuthorRepository.existsByBookIdAndAuthorId(bookId,authorId)) {
            throw new AuthorAlreadyAddedException("Author with id " + authorId + " is already added to this book..");
        }

        BookAuthor bookAuthor = new BookAuthor();
        bookAuthor.setBook(book);
        bookAuthor.setAuthor(author);
        bookAuthorRepository.save(bookAuthor);

        log.info("Book with ID {} is added to author ID {}", bookId, authorId);

    }
}