package com.example.WineOclocK.spring.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class Rating {

//  - 검색 필터링 결과 와인들 (1점)
//  - 디테일한 와인 (2점)
//  - 저장한 와인 (3점)
//  - 평가하기 (4점)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;
    private Long userId;
    private Long wineId;
    private int rating;

    /**
     * update 기능
     */
    public void update(int rating) {
        this.rating = rating;
    }
}
