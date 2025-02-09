package org.springdemo.springproject.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.dto.BookDTO;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.exception.AuthorNotFoundException;
import org.springdemo.springproject.exception.BookNotFoundException;
import org.springdemo.springproject.repository.AuthorRepository;
import org.springdemo.springproject.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional // Ensures atomicity for DB operations
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;


    //
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

    @Override
    public BookDTO addAuthorToBook(Long bookId, Long authorId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id : "+bookId+" not found"));

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException("Author with id : "+authorId+" not found"));

        book.getAuthors().add(author);
        Book updatedBook = bookRepository.save(book);

        return convertToDTO(updatedBook);
    }

    @Override
    public BookDTO removeAuthorFromBook(Long bookId, Long authorId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id : "+bookId+" not found"));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() ->new AuthorNotFoundException("Author with id : "+authorId+" not found"));

        book.getAuthors().remove(author);
        Book updatedBook = bookRepository.save(book);
        return convertToDTO(book);
    }

    // Helper method to convert Book entity to BookDTO
    private BookDTO convertToDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getPublishYear(),
                book.getAuthors() != null
                        ? book.getAuthors().stream().map(Author::getId).collect(Collectors.toList())
                        : new ArrayList<>() // Handle null case
        );
    }
}
