package com.project.audiobook.repository;

import com.project.audiobook.entity.AudioBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioBookRepository extends JpaRepository<AudioBook, Long> {

}
