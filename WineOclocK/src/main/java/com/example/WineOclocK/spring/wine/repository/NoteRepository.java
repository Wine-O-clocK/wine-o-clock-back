package com.example.WineOclocK.spring.wine.repository;

import com.example.WineOclocK.spring.domain.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository <Note, Long> {

    void deleteByUserIdAndWineId(Long userId, Long wineId);
}
