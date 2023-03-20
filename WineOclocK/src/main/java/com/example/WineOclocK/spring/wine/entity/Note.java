package com.example.WineOclocK.spring.wine.entity;

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
public class Note {

    /**
     * 테이스팅 노트
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;
    private Long userId;
    private Long wineId;
    private int grade;
    private String review;

    /**
     * update 기능
     */
    public void update(int grade, String review) {
        this.grade = grade;
        this.review = review;
    }
}
