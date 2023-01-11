package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineRepository extends JpaRepository<Wine, Long> {
}
