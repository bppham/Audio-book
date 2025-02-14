package com.project.audiobook.controller;

import com.project.audiobook.dto.VoiceDTO;
import com.project.audiobook.mapper.VoiceMapper;
import com.project.audiobook.service.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/voices")
public class VoiceController {
    
    private final VoiceService voiceService;
    private final VoiceMapper voiceMapper;

    @Autowired
    public VoiceController(VoiceService voiceService, VoiceMapper voiceMapper) {
        this.voiceService = voiceService;
        this.voiceMapper = voiceMapper;
    }

    @PostMapping
    public ResponseEntity<VoiceDTO> addVoice(@RequestBody VoiceDTO voiceDTO) {
        return ResponseEntity.ok(voiceService.addVoice(voiceDTO));
    }

    @GetMapping
    public ResponseEntity<List<VoiceDTO>> getAllVoices() {
        return ResponseEntity.ok(voiceService.getAllVoice());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoiceDTO> getVoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(voiceService.getVoiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoiceDTO> updateVoice(@PathVariable Long id, @RequestBody VoiceDTO voiceDTO) {
        return ResponseEntity.ok(voiceService.updateVoice(id, voiceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVoice(@PathVariable Long id) {
        voiceService.deleteVoice(id);
        return ResponseEntity.ok("Voice delete successfully");
    }


}
