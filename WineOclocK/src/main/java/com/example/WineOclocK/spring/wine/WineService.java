package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Rating;
import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.entity.Wine;
import com.example.WineOclocK.spring.user.UserRepository;
import com.example.WineOclocK.spring.wine.dto.SearchReqDto;
import com.example.WineOclocK.spring.wine.dto.SearchWineDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.OracleJoinFragment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WineService {

    private final WineRepository wineRepository;
    private final RatingRepository ratingRepository;

    /**
     * json 형식으로 fastApi 에게 보냄
     *
     * {
     *   "userId": "1",
     *   "result": " wine['wineType'] + ' ' + wine['wineSweet'] + '당도 ' + wine['wineBody'] + '바디 ' + wine['aroma1'] + ' ' + wine['aroma2'] + ' ' + wine['aroma3'] "
     * }
     */
    // 콘텐츠기반 추천 알고리즘 데이터 전처리
    public Map<String, Object> recommendContent(User user) throws IOException {

        StringBuilder sb = new StringBuilder();
        Map<String, Object> map = new HashMap<>();

        //유저정보를 전처리화
        sb.append(user.getUserLikeType()).append(" ");
        map.put("userLikeType", user.getUserLikeType());

        // 와인당보 : 0 (단 와인 선호), 1(단 와인 불호), 2(상관없음)
        if (user.getUserLikeSweet() == 0){
            sb.append("5당도").append(" ");
            map.put("userLikeSweet", "5");
        } else if (user.getUserLikeSweet() == 1) {
            sb.append("1당도").append(" ");
            map.put("userLikeSweet", "1");
        } else {
            sb.append("3당도").append(" ");
            map.put("userLikeSweet", "3");
        }

        // 와인바디 : 0 (가벼운 와인 선호), 1 (무거운 와인 선호), 2 (상관없음)
        if (user.getUserLikeBody() == 0){
            sb.append("1바디").append(" ");
            map.put("userLikeBody", "1");
        } else if (user.getUserLikeBody() == 1) {
            sb.append("5바디").append(" ");
            map.put("userLikeBody", "5");}
        else {
            sb.append("3바디").append(" ");
            map.put("userLikeBody", "3");
        }

        map.put("userLikeAroma1", user.getUserLikeAroma1());
        map.put("userLikeAroma2", user.getUserLikeAroma2());
        map.put("userLikeAroma3", user.getUserLikeAroma3());

        sb.append(user.getUserLikeAroma1()).append(" ");
        sb.append(user.getUserLikeAroma2()).append(" ");
        sb.append(user.getUserLikeAroma3());

        map.put("userWineStr", sb.toString());

        return map;
    }

    public Map<String, Object> recommendItem() throws IOException {
        Map<String, Object> map = new HashMap<>();

        List<Rating> ratings = ratingRepository.findAll();

        map.put("ratings", ratings);

        return map;
    }

    public List<Wine> flaskResponseParsing(String string) {

        string = string.substring(1, string.length()-2); // 앞 뒤 '[', ']' 제거
        String[] strSplit = string.split(","); // , 를 기준으로 나눔

        List<Wine> wineResult = new ArrayList<>();

        for(String s : strSplit) {
            Wine wine = wineRepository.findById(Long.parseLong(s))
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 와인입니다."));
            wineResult.add(wine);
        }
        return wineResult;
    }

    @Transactional
    public List<SearchWineDto> searchWines (String keyword){
        System.out.println("--------- wineSearch 시작 : " + keyword + "와인을 검색합니다");

        List<Wine> wines = wineRepository.findByWineNameContaining(keyword);
        List<SearchWineDto> wineDtoList = new ArrayList<>();

        if(wines.isEmpty()) {
            System.out.println("와인이 없습니다");
            return wineDtoList;
        }

        for(Wine wine : wines) {
            wineDtoList.add(this.convertEntityToDto(wine));
        }
        return wineDtoList;
    }

    public SearchWineDto convertEntityToDto(Wine wine){

        return SearchWineDto.builder()
                .wineName(wine.getWineName())
                .wineNameEng(wine.getWineNameEng())
                .wineImage(wine.getWineImage())
                .wineType(wine.getWineType())
                .winePrice(wine.getWinePrice())
                .wineVariety(wine.getWineVariety())
                .wineSweet(wine.getWineSweet())
                .wineBody(wine.getWineBody())
                .aroma1(wine.getAroma1())
                .aroma2(wine.getAroma2())
                .aroma3(wine.getAroma3()).
                build();
    }

    public List<SearchWineDto> searchByFiltering(SearchReqDto searchReqDto) {
        Specification<Wine> spec = (root, query, criteriaBuilder) -> null;

        //ex. 아로마 -> '사과', '베리', null
        // 사과결과 -> 아로마1='사과' or 아로마2='사과' or 아로마3='사과'
        // 베리결과 -> 아로마1='베리' or 아로마2='베리' or 아로마3='베리'
        // 아로마결과 -> 사과결과 or 베리결과
        if (searchReqDto.getAroma1() != null) {
            spec = spec.or(WineSpecification.equalAroma1(searchReqDto.getAroma1()));
            spec = spec.or(WineSpecification.equalAroma2(searchReqDto.getAroma1()));
            spec = spec.or(WineSpecification.equalAroma3(searchReqDto.getAroma1()));
        }
        if (searchReqDto.getAroma2() != null){
            spec = spec.or(WineSpecification.equalAroma1(searchReqDto.getAroma2()));
            spec = spec.or(WineSpecification.equalAroma2(searchReqDto.getAroma2()));
            spec = spec.or(WineSpecification.equalAroma3(searchReqDto.getAroma2()));
        }
        if (searchReqDto.getAroma3() != null){
            spec = spec.or(WineSpecification.equalAroma1(searchReqDto.getAroma3()));
            spec = spec.or(WineSpecification.equalAroma2(searchReqDto.getAroma3()));
            spec = spec.or(WineSpecification.equalAroma3(searchReqDto.getAroma3()));
        }
        if (searchReqDto.getType() != null) spec = spec.and(WineSpecification.equalWineType(searchReqDto.getType()));
        if (searchReqDto.getPrice() != 0) spec = spec.and(WineSpecification.equalWinePrice(searchReqDto.getPrice()));

        List<Wine> wines = wineRepository.findAll(spec);
        List<SearchWineDto> wineDtoList = new ArrayList<>();

        if(wines.isEmpty()) {
            System.out.println("와인이 없습니다");
            return wineDtoList;
        }

        for(Wine wine : wines) {
            wineDtoList.add(this.convertEntityToDto(wine));
        }
        return wineDtoList;
    }
}
