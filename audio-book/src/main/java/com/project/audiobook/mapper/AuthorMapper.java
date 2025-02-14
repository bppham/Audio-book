package com.project.audiobook.mapper;

import com.project.audiobook.dto.AuthorDTO;
import com.project.audiobook.entity.Author;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthorMapper {
    public AuthorDTO toDTO(Author author){
        return new AuthorDTO(author.getId(), author.getName(), author.getAudioBookCount());
    }

    public Author toEntity(AuthorDTO authorDTO){
        return new Author(authorDTO.getId(), authorDTO.getName(), new ArrayList<>());
    }

}
