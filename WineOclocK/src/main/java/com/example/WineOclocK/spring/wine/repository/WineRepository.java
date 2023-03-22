package com.example.WineOclocK.spring.wine.repository;

import com.example.WineOclocK.spring.wine.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WineRepository extends JpaRepository<Wine, Long>, JpaSpecificationExecutor<Wine> {

    Optional<Wine> findByWineName(String wineName);

    List<Wine> findByWineNameContaining(@Param("name") String name);
}
