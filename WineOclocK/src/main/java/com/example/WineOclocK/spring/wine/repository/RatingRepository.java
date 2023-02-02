package com.example.WineOclocK.spring.wine.repository;

import com.example.WineOclocK.spring.domain.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    boolean existsByUserIdAndWineId(Long userId, Long wineId);
    void deleteByUserIdAndWineId(Long userId, Long wineId);
    Optional<Rating> findByUserIdAndWineId(Long userId, Long wineId);
}
