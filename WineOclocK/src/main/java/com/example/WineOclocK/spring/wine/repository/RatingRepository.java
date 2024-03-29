package com.example.WineOclocK.spring.wine.repository;

import com.example.WineOclocK.spring.wine.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    boolean existsByUserIdAndWineId(Long userId, Long wineId);
    Optional<Rating> findByUserIdAndWineId(Long userId, Long wineId);
    List<Rating> findByUserIdOrderByRatingDesc(Long userId);
    List<Rating> findAllyByUserId(Long userId);
}
