package com.project.audiobook.repository;

import com.project.audiobook.entity.Voice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceRepository extends JpaRepository<Voice, Long> {
    boolean existsByName(String name);
}
