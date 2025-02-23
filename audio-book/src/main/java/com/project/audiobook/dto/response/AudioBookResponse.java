package com.project.audiobook.dto.response;

import com.project.audiobook.dto.request.AudioFile.AudioFileRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AudioBookResponse {
    private Long id;
    private String title;
    private Long authorId;
    private String image;
    private Long voiceId;
    private String description;
    private String note;
    private List<Long> categoryIds;
    private List<AudioFileResponse> audioFiles;
}
