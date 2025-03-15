package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springdemo.springproject.dto.AuthorDTO;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.entity.BookAuthor;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    private Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true) //improve performance
    public List<Book> getBooksByAuthorId(Long authorId) {
        log.info("Fetching books for author with ID {}...", authorId);
        Author author = findAuthorById(authorId);

        List<Book> books = author.getBookAuthors().stream()
                .map(BookAuthor::getBook)
                .collect(Collectors.toList());

        log.info("Found {} books for author with ID {}", books.size(), authorId);
        return books;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        log.info("Fetching all authors...");
        List<Author> authors = authorRepository.findAll();
        log.info("Found {} authors", authors.size());
        return authors;
    }

    @Override
    @Transactional(readOnly = true)
    public Author getById(Long authorId) {
        log.info("Fetching author with ID {}...", authorId);
        Author author = findAuthorById(authorId);
        log.info("Author with ID {} fetched successfully", authorId);
        return findAuthorById(authorId);
    }

    @Override
    public List<Author> getAuthorsByNationality(String nationality) {
        log.info("Fetching authors for nationality {}", nationality);
        List<Author> authors = authorRepository.findByNationality(nationality);
        log.info("Found {} authors for nationality {}", authors.size(), nationality);
        return authors;
    }

    @Override
    @Transactional
    public Author createAuthor(AuthorDTO authorDTO) {
        log.info("Creating new author with name {}...", authorDTO.getName());
        Author author = modelMapper.map(authorDTO, Author.class);
        Author savedAuthor = authorRepository.save(author);
        log.info("Author created with ID {}", savedAuthor.getId());
        return savedAuthor;
    }

    @Override
    @Transactional
    public Author updateAuthor(Long authorId, AuthorDTO authorDTO) {
        log.info("Updating author with ID {}...", authorId);
        Author author = findAuthorById(authorId);
        modelMapper.map(authorDTO, author);
        Author updatedAuthor = authorRepository.save(author);
        log.info("Successfully updated author with ID {}", authorId);
        return updatedAuthor;
    }

    @Override
    @Transactional
    public void deleteAuthor(Long authorId) {
        log.info("Deleting author with ID {}...", authorId);
        Author author = findAuthorById(authorId);
        authorRepository.delete(author);
        log.info("Deleted author with ID {}", authorId);
    }

}