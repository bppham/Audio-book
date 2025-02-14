package com.project.audiobook.service.Impl;

import com.project.audiobook.dto.AudioBookDTO;
import com.project.audiobook.dto.AudioFileDTO;
import com.project.audiobook.entity.*;
import com.project.audiobook.mapper.AudioBookMapper;
import com.project.audiobook.mapper.AudioFileMapper;
import com.project.audiobook.repository.AudioBookRepository;
import com.project.audiobook.repository.AuthorRepository;
import com.project.audiobook.repository.CategoryRepository;
import com.project.audiobook.repository.VoiceRepository;
import com.project.audiobook.service.AudioBookService;
import com.project.audiobook.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AudioBookServiceImpl implements AudioBookService{
    private final AudioBookRepository audioBookRepository;
    private final CategoryRepository categoryRepository;
    private final AudioBookMapper audioBookMapper;
    private final AudioFileMapper audioFileMapper;
    private final AuthorRepository authorRepository;
    private final VoiceRepository voiceRepository;
    @Autowired
    private FileStorageService fileStorageService;


    @Override
    public AudioBookDTO createAudioBook(AudioBookDTO audioBookDTO, MultipartFile imageFile, List<MultipartFile> audioFiles) {
        List<Category> categories = audioBookDTO.getCategories().stream()
                .map(categoryDTO -> categoryRepository.findById(categoryDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found")))
                .collect(Collectors.toList());
        // Lấy tác giả
        Author author = authorRepository.findById(audioBookDTO.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        // Lấy giọng đọc
        Voice voice = voiceRepository.findById(audioBookDTO.getVoice().getId())
                .orElseThrow(() -> new RuntimeException("Voice not found"));
        // Image
        String imageUrl = (imageFile != null)? fileStorageService.storeFile(imageFile) : null;
        audioBookDTO.setImage(imageUrl);
        // Audio Files
        List<AudioFileDTO> uploadedAudioFiles = new ArrayList<>();
        if (audioFiles != null) {
            for (MultipartFile file : audioFiles) {
                String fileUrl = fileStorageService.storeFile(file);
                uploadedAudioFiles.add(new AudioFileDTO(null, file.getOriginalFilename(), fileUrl));
            }
        }
        audioBookDTO.setAudioFiles(uploadedAudioFiles);

        AudioBook audioBook = audioBookMapper.toEntity(audioBookDTO, categories, author, voice);
        audioBookRepository.save(audioBook);
        return audioBookMapper.toDTO(audioBook);
    }

    @Override
    public AudioBookDTO updateAudioBook(Long id, AudioBookDTO audioBookDTO, MultipartFile imageFile, List<MultipartFile> audioFiles) {
        AudioBook existingAudioBook = audioBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AudioBook not found"));
        // Lấy tác giả
        Author author = authorRepository.findById(audioBookDTO.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        // Lấy giọng đọc
        Voice voice = voiceRepository.findById(audioBookDTO.getVoice().getId())
                .orElseThrow(() -> new RuntimeException("Voice not found"));

        // Update category
        List<Category> categories = audioBookDTO.getCategories().stream()
                .map(categoryDTO -> categoryRepository.findById(categoryDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found")))
                .collect(Collectors.toList());

        // Update image
        if(imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileStorageService.storeFile(imageFile);
            audioBookDTO.setImage(imageUrl);
            System.out.println("Have new image: " + imageUrl);
        } else {
            audioBookDTO.setImage(existingAudioBook.getImage());
            System.out.println("Old image");
        }

        // Update audio files
        List<AudioFile> existingFiles = existingAudioBook.getAudioFiles();
        if (audioFiles != null && !audioFiles.isEmpty()) {
            existingFiles.clear();
            for (MultipartFile file : audioFiles) {
                String fileUrl = fileStorageService.storeFile(file);
                System.out.println("Updating audio: " + fileUrl);
                existingFiles.add(new AudioFile(null, file.getOriginalFilename(), fileUrl, existingAudioBook));
            }
        }
        // Update other information
        existingAudioBook.setTitle(audioBookDTO.getTitle());
        existingAudioBook.setAuthor(author);
        existingAudioBook.setVoice(voice);
        existingAudioBook.setDescription(audioBookDTO.getDescription());
        existingAudioBook.setNote(audioBookDTO.getNote());
        existingAudioBook.setImage(audioBookDTO.getImage());
        existingAudioBook.setCategories(categories);
        existingAudioBook.setAudioFiles(existingFiles);

        audioBookRepository.save(existingAudioBook);
        return audioBookMapper.toDTO(existingAudioBook);
    }

    @Override
    public void deleteAudioBook(Long id) {
        if(!audioBookRepository.existsById(id)) {
            throw new RuntimeException("AudioBook not found");
        } else {
            audioBookRepository.deleteById(id);
        }
    }

    @Override
    public AudioBookDTO getAudioBookById(Long id) {
        AudioBook audioBook = audioBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AudioBook not found"));
        return audioBookMapper.toDTO(audioBook);
    }

    @Override
    public List<AudioBookDTO> getAllAudioBooks() {
        List<AudioBook> audioBooks = audioBookRepository.findAll();
        List<AudioBookDTO> audioBookDTOs = audioBooks.stream()
                .map(audioBookMapper::toDTO)
                .collect(Collectors.toList());
        return audioBookDTOs;
    }
}
