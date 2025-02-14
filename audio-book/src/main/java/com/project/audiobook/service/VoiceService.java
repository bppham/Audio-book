package com.project.audiobook.service;

import com.project.audiobook.dto.VoiceDTO;

import java.util.List;

public interface VoiceService {
    VoiceDTO addVoice(VoiceDTO voiceDTO);
    List<VoiceDTO> getAllVoice();
    VoiceDTO updateVoice(Long id, VoiceDTO voiceDTO);
    VoiceDTO getVoiceById(Long id);
    void deleteVoice(Long id);
}
