package com.example.WineOclocK.spring.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
public class Crawling {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private List<String> mention;   // 한달 가장 언급 많은 순위
    private List<String> access;    // 접근성 순위
    private List<String> present;   // 선물 순위
    private List<String> price;     // 가성비 순위
}
