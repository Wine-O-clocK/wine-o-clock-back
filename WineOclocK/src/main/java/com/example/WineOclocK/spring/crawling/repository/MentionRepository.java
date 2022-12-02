package com.example.WineOclocK.spring.crawling.repository;

import com.example.WineOclocK.spring.crawling.response.Access;
import com.example.WineOclocK.spring.crawling.response.Mention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepository extends JpaRepository<Mention, Long> {
}
