package com.project.audiobook.mapper;

import com.project.audiobook.dto.request.Author.AuthorRequest;
import com.project.audiobook.dto.request.Category.CategoryRequest;
import com.project.audiobook.dto.response.AuthorResponse;
import com.project.audiobook.entity.Author;
import com.project.audiobook.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toAuthor(AuthorRequest request);
    @Mapping(target = "audioBookCount", expression = "java(author.getAudioBooks() != null ? author.getAudioBooks().size() : 0)")
    AuthorResponse toAuthorResponse(Author author);
}
