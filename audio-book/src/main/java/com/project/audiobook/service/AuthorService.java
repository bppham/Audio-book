package com.project.audiobook.service;

import com.project.audiobook.dto.AuthorDTO;

import java.util.List;


public interface AuthorService {
    AuthorDTO addAuthor(AuthorDTO authorDTO);
    List<AuthorDTO> getAllAuthors();
    AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO);
    AuthorDTO getAuthorById(Long id);
    void deleteAuthor(Long id);
}
