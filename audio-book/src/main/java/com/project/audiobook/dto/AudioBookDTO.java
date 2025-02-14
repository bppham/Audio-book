package com.project.audiobook.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AudioBookDTO {
    private Long id;
    private String title;
    private AuthorDTO author;
    private String image;
    private VoiceDTO voice;
    private String description;
    private String note;
    private List<CategoryDTO> categories;
    private List<AudioFileDTO> audioFiles;
}
