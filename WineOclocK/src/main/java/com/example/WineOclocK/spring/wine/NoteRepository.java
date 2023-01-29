package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Rating, Long> {
}
