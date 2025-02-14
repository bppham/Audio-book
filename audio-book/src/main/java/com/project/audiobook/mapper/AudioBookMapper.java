package com.project.audiobook.mapper;

import com.project.audiobook.dto.AudioBookDTO;
import com.project.audiobook.dto.AuthorDTO;
import com.project.audiobook.dto.VoiceDTO;
import com.project.audiobook.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AudioBookMapper {

    private final CategoryMapper categoryMapper = new CategoryMapper();
    private final AudioFileMapper audioFileMapper = new AudioFileMapper();
    private final AuthorMapper authorMapper = new AuthorMapper();
    private final VoiceMapper voiceMapper = new VoiceMapper();

    public AudioBookDTO toDTO(AudioBook audioBook) {
        return new AudioBookDTO(
                audioBook.getId(),
                audioBook.getTitle(),
                authorMapper.toDTO(audioBook.getAuthor()),
                audioBook.getImage(),
                voiceMapper.toDTO(audioBook.getVoice()),
                audioBook.getDescription(),
                audioBook.getNote(),
                audioBook.getCategories().stream().map(categoryMapper::toDTO).collect(Collectors.toList()),
                audioBook.getAudioFiles().stream().map(audioFileMapper::toDTO).collect(Collectors.toList())
        );
    }

    public AudioBook toEntity(AudioBookDTO dto, List<Category> categories, Author author, Voice voice) {
        AudioBook audioBook = new AudioBook();
        audioBook.setTitle(dto.getTitle());
        audioBook.setAuthor(author);
        audioBook.setImage(dto.getImage());
        audioBook.setVoice(voice);
        audioBook.setDescription(dto.getDescription());
        audioBook.setNote(dto.getNote());
        audioBook.setCategories(categories);

        // Ánh xạ danh sách audioFiles từ DTO sang Entity
        List<AudioFile> audioFiles = dto.getAudioFiles().stream()
                .map(fileDTO -> {
                    AudioFile file = new AudioFile();
                    file.setFileName(fileDTO.getFileName());
                    file.setFileUrl(fileDTO.getFileUrl());
                    file.setAudioBook(audioBook); // Quan trọng: Gán audioBook để thiết lập quan hệ ManyToOne
                    return file;
                }).collect(Collectors.toList());

        audioBook.setAudioFiles(audioFiles);

        return audioBook;
    }
}