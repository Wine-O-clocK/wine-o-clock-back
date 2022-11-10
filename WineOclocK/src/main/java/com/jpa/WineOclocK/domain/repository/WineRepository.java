package com.jpa.WineOclocK.domain.repository;

import com.jpa.WineOclocK.domain.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineRepository extends JpaRepository<Wine, Long> {
}
