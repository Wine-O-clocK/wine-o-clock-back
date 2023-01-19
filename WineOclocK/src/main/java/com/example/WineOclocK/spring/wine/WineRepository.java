package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WineRepository extends JpaRepository<Wine, Long>, JpaSpecificationExecutor<Wine> {

//    Optional<Wine> findByWineName(String wineName);

    //@Query(value = "SELECT w FROM Wine w WHERE w.name LIKE %?1%")
    List<Wine> findByWineNameContaining(@Param("name") String name);
    List<Wine> findByWineType(String wineType);
    List<Wine> findByWinePrice(int price);
}
