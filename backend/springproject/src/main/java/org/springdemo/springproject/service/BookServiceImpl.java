package org.springdemo.springproject.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.dto.BookDTO;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.BookAuthor;
import org.springdemo.springproject.exception.AuthorNotFoundException;
import org.springdemo.springproject.exception.BookNotFoundException;
import org.springdemo.springproject.repository.AuthorRepository;
import org.springdemo.springproject.repository.BookAuthorRepository;
import org.springdemo.springproject.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookAuthorRepository bookAuthorRepository;


    @Override
    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
        return convertToDTO(book);
    }

    @Override
    public BookDTO createBook(BookDTO bookDto) {
        Book newBook = new Book();
        newBook.setTitle(bookDto.getTitle());
        newBook.setPublishYear(bookDto.getPublishYear());

        Book savedBook = bookRepository.save(newBook);
        return convertToDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found, cannot update"));

        existingBook.setTitle(bookDto.getTitle());
        existingBook.setPublishYear(bookDto.getPublishYear());

        Book updatedBook = bookRepository.save(existingBook);
        return convertToDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with ID " + id + " not found, cannot delete");
        }
        bookRepository.deleteById(id);
    }


    private BookDTO convertToDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getPublishYear(),
                book.getBookAuthors() != null
                        ? book.getBookAuthors().stream().map(bookAuthor -> bookAuthor.getAuthor().getId()).collect(Collectors.toList())
                        : new ArrayList<>() // Handle null case
        );
    }



}
