package com.project.audiobook.mapper;

import com.project.audiobook.dto.AudioFileDTO;
import com.project.audiobook.entity.AudioBook;
import com.project.audiobook.entity.AudioFile;
import org.springframework.stereotype.Component;

@Component
public class AudioFileMapper {
    public AudioFileDTO toDTO(AudioFile audioFile) {
        return new AudioFileDTO(audioFile.getId(),
                audioFile.getFileName(),
                audioFile.getFileUrl());
    }

    public AudioFile toEntity(AudioFileDTO audioFileDTO, AudioBook audioBook) {
        return new AudioFile(null, audioFileDTO.getFileName(), audioFileDTO.getFileUrl(), audioBook);
    }
}
