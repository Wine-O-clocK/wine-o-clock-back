package com.example.WineOclocK.spring.wine.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class SearchReqDto {
    private String type;
    private int price;

    private ArrayList<String> aroma;
}
