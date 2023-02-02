package com.example.WineOclocK.spring.wine.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoteReqDto {
    private int grade;
    private String review;
}
