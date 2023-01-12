package com.example.WineOclocK.spring.wine.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WineDto {

    private String wineImage;
    private String wineName;
    private String wineNameEng;
    private String wineType; //와인 종류 (레드, 화이트, 로제, 스파클링)
    private int winePrice; //와인 가격

    private int wineSweet; //와인 당도감 (1~5)
    private int wineBody; //와인 바디감 (1~5)
    private String wineVariety; //와인 품종

    private String aroma1;
    private String aroma2; //null 가능
    private String aroma3; //null 가능
}
