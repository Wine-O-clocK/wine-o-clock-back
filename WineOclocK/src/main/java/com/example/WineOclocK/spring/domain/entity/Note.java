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
public class Note {

    /**
     * 테이스팅 노트
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;
    private Long userId;
    private Long wineId;
    private String content;
}
