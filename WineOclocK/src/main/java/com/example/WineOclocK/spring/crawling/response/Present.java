package com.example.WineOclocK.spring.crawling.response;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Present {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long presentId;

    private String wineImage;
    private String wineName;
    private String wineNameEng;
    private String wineType;
    private int wineSweet;
    private int wineBody;
    private String wineVariety;
}
