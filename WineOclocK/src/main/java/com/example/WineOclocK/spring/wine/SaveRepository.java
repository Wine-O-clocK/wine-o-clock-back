package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Rating;
import com.example.WineOclocK.spring.domain.entity.Save;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveRepository extends JpaRepository<Save, Long> {

    boolean existsByUserIdAndWineId(String email);
}
