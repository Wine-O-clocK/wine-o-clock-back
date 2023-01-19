package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Wine;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Map;

public class WineSpecification {

    public static Specification<Wine> equalWineType(String type) {
        //entity 의 "wineType" 필드가 파라메터 "type"인 요소 선택
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("wineType"), type);
    }

    public static Specification<Wine> equalWinePrice(int price) {
        // entity 의 "winePrice" 필드가 파라메터 "price"인 요소 선택
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("winePrice"), price);
    }

    public static Specification<Wine> equalAroma1(String aroma) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("aroma1"), aroma);
    }

    public static Specification<Wine> equalAroma2(String aroma) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("aroma2"), aroma);
    }

    public static Specification<Wine> equalAroma3(String aroma) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("aroma3"), aroma);
    }

//    public static Specification<Wine> searchWine(Map<String, Object> searchKey){
//        return ((root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            for(String key : searchKey.keySet()){
//                predicates.add(criteriaBuilder.equal(root.get(key), searchKey.get(key)));
//            }
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        });
//    }
}