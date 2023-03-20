package com.example.WineOclocK.spring.wine.repository;

import com.example.WineOclocK.spring.wine.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository <Note, Long> {
    Note findByUserIdAndWineId(Long userId, Long wineId);
    List<Note> findAllByUserId(Long userId);

    boolean existsByUserIdAndWineId(Long userId, Long wineId);

    void deleteByUserIdAndWineId(Long userId, Long wineId);
}
