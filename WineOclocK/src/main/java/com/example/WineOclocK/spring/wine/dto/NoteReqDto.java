package com.example.WineOclocK.spring.wine.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoteReqDto {
    private Long userId;
    private Long wineId;
    private int grade;
    private String content;
}
