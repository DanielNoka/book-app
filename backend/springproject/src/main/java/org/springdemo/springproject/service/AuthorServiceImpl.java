package org.springdemo.springproject.service;

import lombok.AllArgsConstructor;
import org.springdemo.springproject.dto.AuthorDTO;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.entity.Book;
import org.springdemo.springproject.exception.AuthorNotFoundException;
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
public class AuthorServiceImpl implements AuthorService {


    private AuthorRepository authorRepository;
    private BookRepository bookRepository;


    @Override
    public List<AuthorDTO> getAll() {
        return authorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public AuthorDTO getById(Long id) {
        Author author = authorRepository.findById(id)
                 .orElseThrow(() -> new AuthorNotFoundException("Author with ID " + id + " not found"));
        return convertToDTO(author);
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDto) {
        Author author = new Author();
        author.setName(authorDto.getName());
        author.setEmail(authorDto.getEmail());
        author.setDescription(authorDto.getDescription());
        author.setNationality(authorDto.getNationality());

        Author createAuthor = authorRepository.save(author);
        return convertToDTO(createAuthor);

    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDto) {
        Author author = authorRepository.findById(id)
                        .orElseThrow(() -> new AuthorNotFoundException("Author with ID " + id + " not found"));

        author.setName(authorDto.getName());
        author.setEmail(authorDto.getEmail());
        author.setDescription(authorDto.getDescription());
        author.setNationality(authorDto.getNationality());

        Author updatedAuthor = authorRepository.save(author);
        return convertToDTO(updatedAuthor);

    }

    @Override
    public void deleteAuthor(Long id) {
        if(!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found");
        }
        authorRepository.deleteById(id);

    }



    private AuthorDTO convertToDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getEmail(),
                author.getNationality(),
                author.getDescription(),
                author.getBooks() != null
                        ? author.getBooks().stream().map(Book::getId).collect(Collectors.toList())
                        : new ArrayList<>() // Handle null case
        );
    }
}
