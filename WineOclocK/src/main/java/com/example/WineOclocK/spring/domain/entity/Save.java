package com.example.WineOclocK.spring.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Save {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saveId;
    private Long userId;
    private Long wineId;
}
