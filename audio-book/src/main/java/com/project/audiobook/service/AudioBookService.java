package com.project.audiobook.service;

import com.project.audiobook.dto.AudioBookDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AudioBookService {
    AudioBookDTO createAudioBook(AudioBookDTO AudioBookDTO, MultipartFile imageFile, List<MultipartFile> audioFiles);
    AudioBookDTO updateAudioBook(Long id, AudioBookDTO audioBookDTO, MultipartFile imageFile, List<MultipartFile> audioFiles);
    void deleteAudioBook(Long id);
    AudioBookDTO getAudioBookById(Long id);
    List<AudioBookDTO> getAllAudioBooks();
}
