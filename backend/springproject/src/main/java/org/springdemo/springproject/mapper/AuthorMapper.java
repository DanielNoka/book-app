package org.springdemo.springproject.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springdemo.springproject.dto.AuthorDTO;
import org.springdemo.springproject.dto.CreateAuthorDTO;
import org.springdemo.springproject.entity.Author;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    public AuthorDTO toDTO(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getEmail(),
                author.getNationality(),
                author.getDescription(),
                author.getBookAuthors() != null
                        ? author.getBookAuthors().stream()
                        .map(bookAuthor -> bookAuthor.getBook().getId())
                        .collect(Collectors.toList())
                        : List.of() //handle the null cases
        );
    }


    public Author toEntity(CreateAuthorDTO authorDTO) {
        Author author = new Author();

        author.setName(authorDTO.getName());
        author.setEmail(authorDTO.getEmail());
        author.setNationality(authorDTO.getNationality());
        author.setDescription(authorDTO.getDescription());
        return author;
    }

}

