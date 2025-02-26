package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
@Transactional // Ensures atomicity for DB operations
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + authorId + " not found"));

        return author.getBookAuthors().stream()
                .map(BookAuthor::getBook)
                .collect(Collectors.toList());
    }


    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }


    @Override
    public Author getById(Long id) {
        return  authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public Author createAuthor(CreateAuthorDTO createAuthorDTO) {

       Author author = modelMapper.map(createAuthorDTO, Author.class);

       return authorRepository.save(author);
    }

    @Override
    @Transactional
    public Author updateAuthor(Long id, CreateAuthorDTO createAuthorDTO) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + id + " not found"));

        modelMapper.map(createAuthorDTO, author);

        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + id + " not found"));
        authorRepository.delete(author);
    }

}