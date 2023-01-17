package com.example.WineOclocK.spring.crawling.repository;

import com.example.WineOclocK.spring.crawling.response.Recent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecentRepository extends JpaRepository<Recent, Long> {
}
