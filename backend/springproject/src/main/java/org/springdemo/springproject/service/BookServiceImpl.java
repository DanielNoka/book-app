package org.springdemo.springproject.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springdemo.springproject.dto.BookDTO;
import org.springdemo.springproject.dto.CreateBookDTO;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.BookAuthor;
import org.springdemo.springproject.exception.AuthorNotFoundException;
import org.springdemo.springproject.exception.BookNotFoundException;
import org.springdemo.springproject.mapper.BookMapper;
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
    private final BookMapper bookMapper;


    @Override
    public List<BookDTO> getAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
        return bookMapper.toDTO(book);
    }

    @Override
    public BookDTO createBook(CreateBookDTO createBookDTO) {

        Book newBook = bookMapper.toEntity(createBookDTO);
        Book savedBook = bookRepository.save(newBook);

        return bookMapper.toDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long id, CreateBookDTO createBookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found, cannot update"));

        existingBook.setTitle(createBookDTO.getTitle());
        existingBook.setPublishYear(createBookDTO.getPublishYear());

        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with ID " + id + " not found, cannot delete");
        }
        bookRepository.deleteById(id);
    }

}
