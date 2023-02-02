package com.example.WineOclocK.spring.wine.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchWineDto {

    private Long wineId;
    private String wineImage;
    private String wineName;
    private String wineNameEng;
    private String wineType;
    private String winePrice;

    private String wineSweet;
    private String wineBody;
    private String wineVariety;

    private String aroma1;
    private String aroma2;
    private String aroma3;
}
