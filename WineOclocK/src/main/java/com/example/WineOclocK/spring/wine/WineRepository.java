package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {

//    Optional<Wine> findByWineName(String wineName);

    //@Query(value = "SELECT w FROM Wine w WHERE w.name LIKE %?1%")
    List<Wine> findByWineNameContaining(@Param("name") String name);

//    타입, 아로마, 가격
//    List<Wine> findAllByWineType(String wineType);
//    List<Wine> findAllByWinePrice(int winePrice);
}
