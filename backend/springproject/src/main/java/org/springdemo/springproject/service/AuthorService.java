package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.AuthorDTO;
import org.springdemo.springproject.entity.Author;

import java.util.List;

public interface AuthorService {


    List<AuthorDTO> getAll();
    AuthorDTO getById(Long id);
    AuthorDTO createAuthor(AuthorDTO authorDto);
    AuthorDTO updateAuthor(Long id, AuthorDTO authorDto);
    void deleteAuthor(Long id);

   }
