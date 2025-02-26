package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springdemo.springproject.dto.CreateAuthorDTO;
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

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        log.info("Fetching books for author with ID {}", authorId);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Author with ID " + authorId + " not found"));

        List<Book> books =  author.getBookAuthors().stream()
                .map(BookAuthor::getBook)
                .collect(Collectors.toList());
        log.info("Found {} books for author with ID {}", books.size(), authorId);
        return books;
    }

    @Override
    public List<Author> getAll() {
        log.info("Fetching all authors");
        List<Author> authors = authorRepository.findAll();
        log.info("Found {} authors", authors.size());
        return authors;
    }

    @Override
    public Author getById(Long id) {
        log.info("Fetching author with ID {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Author with ID " + id + " not found"));
        log.info("Found author with ID {}", author.getId());
        return author;
    }

    @Override
    @Transactional
    public Author createAuthor(CreateAuthorDTO createAuthorDTO) {
        log.info("Creating new author with name {}", createAuthorDTO.getName());
        Author author = modelMapper.map(createAuthorDTO, Author.class);
        Author savedAuthor = authorRepository.save(author);
        log.info("Created new author with ID {}", savedAuthor.getId());
        return savedAuthor;
    }

    @Override
    @Transactional
    public Author updateAuthor(Long id, CreateAuthorDTO createAuthorDTO) {
        log.info("Updating author with ID {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                     new EntityNotFoundException("Author with ID " + id + " not found"));

        modelMapper.map(createAuthorDTO, author);
        Author updatedAuthor = authorRepository.save(author);
        log.info("The author with ID {} is updated", updatedAuthor.getId());
        return updatedAuthor;
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        log.info("Deleting author with ID {}", id);
        Author author = authorRepository.findById(id)
                .orElseThrow(() ->
                    new EntityNotFoundException("Author with ID " + id + " not found"));

        authorRepository.delete(author);
        log.info("Author with ID {} is deleted", id);
    }

}