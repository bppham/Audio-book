package com.project.audiobook.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
    private int audioBookCount;
}
