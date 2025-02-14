package com.project.audiobook.service.Impl;

import com.project.audiobook.dto.VoiceDTO;
import com.project.audiobook.entity.Voice;
import com.project.audiobook.mapper.VoiceMapper;
import com.project.audiobook.repository.VoiceRepository;
import com.project.audiobook.service.VoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoiceServiceImpl implements VoiceService {
    private final VoiceRepository voiceRepository;
    private final VoiceMapper voiceMapper;

    @Override
    public VoiceDTO addVoice(VoiceDTO voiceDTO) {
        if(voiceRepository.existsByName(voiceDTO.getName())) {
            throw new RuntimeException("Voice actor already exists");
        }
        Voice voice = voiceMapper.toEntity(voiceDTO);
        Voice savedVoice = voiceRepository.save(voice);
        return voiceMapper.toDTO(savedVoice);
    }

    @Override
    public List<VoiceDTO> getAllVoice() {
        List<Voice> voices = voiceRepository.findAll();
        return voices.stream()
                .map(voiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VoiceDTO updateVoice(Long id, VoiceDTO voiceDTO) {
        Voice voice = voiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voice actor not found"));
        voice.setName(voiceDTO.getName());
        Voice updatedVoice = voiceRepository.save(voice);
        return voiceMapper.toDTO(updatedVoice);
    }

    @Override
    public VoiceDTO getVoiceById(Long id) {
        Voice voice = voiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voice actor not found"));
        return voiceMapper.toDTO(voice);
    }

    @Override
    public void deleteVoice(Long id) {
        if(!voiceRepository.existsById(id)) {
            throw new RuntimeException("Voice actor not found");
        }
        voiceRepository.deleteById(id);
    }

}
