package com.example.WineOclocK.spring.wine.repository;

import com.example.WineOclocK.spring.wine.entity.Save;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveRepository extends JpaRepository<Save, Long> {
    boolean existsByUserIdAndWineId(Long userId, Long wineId);
    void deleteByUserIdAndWineId(Long userId, Long wineId);
    List<Save> findAllByUserId(Long userId);
}