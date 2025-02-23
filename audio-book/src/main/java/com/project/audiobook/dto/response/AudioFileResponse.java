package com.project.audiobook.dto.response;

import com.project.audiobook.entity.AudioBook;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AudioFileResponse {
    private Long id;
    private String fileName;
    private String fileUrl;
}
