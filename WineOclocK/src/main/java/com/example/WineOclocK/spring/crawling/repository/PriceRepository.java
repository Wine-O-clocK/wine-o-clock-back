package com.example.WineOclocK.spring.crawling.repository;

import com.example.WineOclocK.spring.crawling.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
