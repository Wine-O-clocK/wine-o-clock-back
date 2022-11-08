package com.example.WineOclocK.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Wine {

    private Integer wineId;

    private String wineName;
    private String wineImg;
    private String wineNameEng;
    private String wineType; //와인 종류 (레드, 화이트, 로제, 스파클링)

    private int wineSweet; //와인 당도감 (1~5)
    private int wineBody; //와인 바디감 (1~5)
    private String wineVar; //와인 품종
    private int winePrice; //와인 가격
    private String wineAroma1;
    private String wineAroma2;
    private String wineAroma3;

    public Wine(String wineName, String wineImg, String wineNameEng, String wineType,
                int wineSweet, int wineBody, String wineVar, int winePrice,
                String wineAroma1, String wineAroma2, String wineAroma3) {

        this.wineName = wineName;
        this.wineImg = wineImg;
        this.wineNameEng = wineNameEng;
        this.wineType = wineType;
        this.wineSweet = wineSweet;
        this.wineBody = wineBody;
        this.wineVar = wineVar;
        this.winePrice = winePrice;
        this.wineAroma1 = wineAroma1;
        this.wineAroma2 = wineAroma2;
        this.wineAroma3 = wineAroma3;
    }
}
