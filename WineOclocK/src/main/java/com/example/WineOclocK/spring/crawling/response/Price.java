package com.example.WineOclocK.spring.crawling.response;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long priceId;

    private String wineImg;
    private String wineName;
    private String wineNameEng;
    private String wineType;
    private int wineSweet;
    private int wineBody;
    private String wineVariety;
}
