package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
