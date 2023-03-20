package com.example.WineOclocK.spring.wine.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class Save {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saveId;
    private Long userId;
    private Long wineId;

    /**
     * update 기능
     */
    public void update(Long userId, Long wineId) {
        this.userId = userId;
        this.wineId = wineId;
    }
}
