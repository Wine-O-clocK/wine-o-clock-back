package com.example.WineOclocK.spring.wine.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class SearchReqDto {
    private String type;
    private int price;

    private String aroma1;
    private String aroma2;
    private String aroma3;
}
