package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WineRepository extends JpaRepository<Wine, Long> {

    Optional<Wine> findByWineName(String wineName);

    //input 에 검색할 키워드로 찾기
    List<Wine> findByWineNameContaining(String wineName);

    //타입, 아로마, 가격
    List<Wine> findAllByWineType(String wineType);
    List<Wine> findAllByWinePrice(int winePrice);
}
