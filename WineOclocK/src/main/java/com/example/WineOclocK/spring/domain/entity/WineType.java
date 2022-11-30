package com.example.WineOclocK.spring.domain.entity;

import javax.persistence.*;

public class WineType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wineTypeidx")
    private Long idx;

    private String name;

    public WineType(String name) {
        this.name = name;
    }

//    //연관관계 편의 메서드
//    public void modifyAnimal(Animal animal) {
//        this.animal = animal;
//        animal.getIllnessList().add(this);
//    }
}
