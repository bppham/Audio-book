package com.project.audiobook.mapper;

import com.project.audiobook.dto.VoiceDTO;
import com.project.audiobook.entity.Voice;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class VoiceMapper {
    public VoiceDTO toDTO(Voice voice) {
        return new VoiceDTO(voice.getId(), voice.getName(), voice.getAudioBookCount());
    }

    public Voice toEntity(VoiceDTO voiceDTO) {
        return new Voice(voiceDTO.getId(), voiceDTO.getName(), new ArrayList<>());
    }
}
