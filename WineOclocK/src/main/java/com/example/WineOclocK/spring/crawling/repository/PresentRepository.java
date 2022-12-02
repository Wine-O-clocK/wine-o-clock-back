package com.example.WineOclocK.spring.crawling.repository;

import com.example.WineOclocK.spring.crawling.response.Access;
import com.example.WineOclocK.spring.crawling.response.Present;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresentRepository extends JpaRepository<Present, Long> {
}
