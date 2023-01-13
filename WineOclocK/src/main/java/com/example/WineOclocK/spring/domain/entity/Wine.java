package com.example.WineOclocK.spring.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
public class Wine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wineId;

    private String wineImage;
    private String wineName;
    private String wineNameEng;
    private String wineType; //와인 종류 (레드, 화이트, 로제, 스파클링)
    private String winePrice; //와인 가격

    private String wineSweet; //와인 당도감 (1~5)
    private String wineBody; //와인 바디감 (1~5)
    private String wineVariety; //와인 품종

    private String aroma1;
    private String aroma2; //null 가능
    private String aroma3; //null 가능

//    @Builder
//    public Wine(String wineName, String wineImg, String wineNameEng, String wineType,
//                int wineSweet, int wineBody, String wineVariety, int winePrice,
//                String wineAroma1, String wineAroma2, String wineAroma3) {
//
//        this.wineName = wineName;
//        this.wineImg = wineImg;
//        this.wineNameEng = wineNameEng;
//        this.wineType = wineType;
//        this.wineSweet = wineSweet;
//        this.wineBody = wineBody;
//        this.wineVariety = wineVariety;
//        this.winePrice = winePrice;
//        this.wineAroma1 = wineAroma1;
//        this.wineAroma2 = wineAroma2;
//        this.wineAroma3 = wineAroma3;
//    }
}
