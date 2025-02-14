package com.project.audiobook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.audiobook.dto.AudioBookDTO;
import com.project.audiobook.service.AudioBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/audiobooks")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AudioBookController {
    private final AudioBookService audioBookService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AudioBookDTO> createAudioBook(
            @RequestPart("audioBook") String audioBookJson,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "audioFile", required = false) List<MultipartFile> audioFile) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        AudioBookDTO audioBookDTO = objectMapper.readValue(audioBookJson, AudioBookDTO.class);
        System.out.println("JSON: " + audioBookJson);

        return ResponseEntity.ok(audioBookService.createAudioBook(audioBookDTO, image, audioFile));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AudioBookDTO> updateAudioBook(
            @PathVariable Long id,
            @RequestPart("audioBook") String audioBookJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestPart(value = "audioFiles", required = false) List<MultipartFile> audioFiles) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        AudioBookDTO audioBookDTO = objectMapper.readValue(audioBookJson, AudioBookDTO.class);
        AudioBookDTO updatedAudioBook = audioBookService.updateAudioBook(id, audioBookDTO, imageFile, audioFiles);
        return ResponseEntity.ok(updatedAudioBook);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAudioBook(@PathVariable Long id) {
        audioBookService.deleteAudioBook(id);
        return ResponseEntity.ok("Audiobook delete successfully");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AudioBookDTO> getAudioBookById(@PathVariable Long id){
        AudioBookDTO audioBookDTO = audioBookService.getAudioBookById(id);
        return ResponseEntity.ok(audioBookDTO);
    }

    @GetMapping
    public ResponseEntity<List<AudioBookDTO>> getAllAudioBook() {
        return ResponseEntity.ok(audioBookService.getAllAudioBooks());
    }
}
