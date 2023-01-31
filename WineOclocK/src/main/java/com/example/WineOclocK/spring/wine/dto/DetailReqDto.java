package com.example.WineOclocK.spring.wine.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetailReqDto {
    private Long userId;
    private Long wineId;
}
