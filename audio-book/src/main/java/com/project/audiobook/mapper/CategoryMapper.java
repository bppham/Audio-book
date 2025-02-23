package com.project.audiobook.mapper;

import com.project.audiobook.dto.request.Category.CategoryRequest;
import com.project.audiobook.dto.response.CategoryResponse;
import com.project.audiobook.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);
    @Mapping(target = "audioBookCount", expression = "java(category.getAudioBooks() != null ? category.getAudioBooks().size() : 0)")
    CategoryResponse toCategoryResponse(Category category);
}
