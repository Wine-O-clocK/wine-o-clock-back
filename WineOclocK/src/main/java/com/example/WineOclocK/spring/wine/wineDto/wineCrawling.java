package com.example.WineOclocK.spring.wine.wineDto;

import lombok.Data;

import java.util.List;

@Data
public class wineCrawling {

    private List<String> mention;   // 한달 가장 언급 많은 순위
    private List<String> access;    // 접근성 순위
    private List<String> present;   // 선물 순위
    private List<String> price;     // 가성비 순위

}
