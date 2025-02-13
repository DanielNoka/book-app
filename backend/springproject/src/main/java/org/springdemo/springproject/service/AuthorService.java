package org.springdemo.springproject.service;

import org.springdemo.springproject.dto.AuthorDTO;
import org.springdemo.springproject.dto.CreateAuthorDTO;

import java.util.List;

public interface AuthorService {

    List<AuthorDTO> getAll();
    AuthorDTO getById(Long id);
    AuthorDTO createAuthor(CreateAuthorDTO createAuthorDTO);
    AuthorDTO updateAuthor(Long id, CreateAuthorDTO createAuthorDTO);
    void deleteAuthor(Long id);

   }
