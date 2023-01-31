package com.example.WineOclocK.spring.wine.repository;

import com.example.WineOclocK.spring.domain.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository <Note, Long> {
    Note findByUserIdAndWineId(Long userId, Long wineId);

    void deleteByUserIdAndWineId(Long userId, Long wineId);
}
