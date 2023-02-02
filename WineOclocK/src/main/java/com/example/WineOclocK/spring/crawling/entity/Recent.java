package com.example.WineOclocK.spring.crawling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recentId;

    private Long wineId;
    private String wineImage;
    private String wineName;
    private String wineNameEng;
    private String wineType;
    private int winePrice;
    private int wineSweet;
    private int wineBody;
    private String wineVariety;
    private String aroma1;
    private String aroma2;
    private String aroma3;
}
