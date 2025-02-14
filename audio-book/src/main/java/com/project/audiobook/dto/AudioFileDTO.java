package com.project.audiobook.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AudioFileDTO {
    private Long id;
    private String fileName;
    private String fileUrl;
}
