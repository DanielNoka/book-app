package org.springdemo.springproject.service;

import lombok.AllArgsConstructor;
import org.springdemo.springproject.dto.AuthorDTO;
import org.springdemo.springproject.dto.CreateAuthorDTO;
import org.springdemo.springproject.entity.Author;
import org.springdemo.springproject.exception.AuthorNotFoundException;
import org.springdemo.springproject.mapper.AuthorMapper;
import org.springdemo.springproject.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional // Ensures atomicity for DB operations
public class AuthorServiceImpl implements AuthorService {


    private AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorDTO> getAll() {
       return authorRepository.findAll().stream()
               .map(authorMapper::toDTO)
               .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with ID " + id + " not found"));
        return authorMapper.toDTO(author);
    }

    @Override
    public AuthorDTO createAuthor(CreateAuthorDTO createAuthorDTO) {

        Author author = authorMapper.toEntity(createAuthorDTO);
        Author createAuthor = authorRepository.save(author);

        return authorMapper.toDTO(createAuthor);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, CreateAuthorDTO createAuthorDTO) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author with ID " + id + " not found"));

        author.setName(createAuthorDTO.getName());
        author.setEmail(createAuthorDTO.getEmail());
        author.setDescription(createAuthorDTO.getDescription());
        author.setNationality(createAuthorDTO.getNationality());

        Author updatedAuthor = authorRepository.save(author);

        return authorMapper.toDTO(updatedAuthor);

    }

    @Override
    public void deleteAuthor(Long id) {
        if(!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found");
        }
        authorRepository.deleteById(id);

    }

}