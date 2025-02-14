package com.project.audiobook.mapper;


import com.project.audiobook.dto.CategoryDTO;
import com.project.audiobook.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CategoryMapper {
    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setAudioBookCount(category.getAudioBookCount());
        return dto;
    }

    public Category toEntity(CategoryDTO categoryDTO) {
        return new Category(categoryDTO.getId(), categoryDTO.getName(), new ArrayList<>());
    }

}
