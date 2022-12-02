package com.example.WineOclocK.spring.crawling.response;

import com.example.WineOclocK.spring.domain.entity.Wine;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Access{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long AccessId;

    private String wineImg;
    private String wineName;
    private String wineNameEng;
    private String wineType;
    private int wineSweet;
    private int wineBody;
    private String wineVariety;
}
