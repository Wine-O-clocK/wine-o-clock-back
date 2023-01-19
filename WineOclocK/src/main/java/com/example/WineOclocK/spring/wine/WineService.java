package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.entity.Wine;
import com.example.WineOclocK.spring.user.UserRepository;
import com.example.WineOclocK.spring.wine.dto.SearchReqDto;
import com.example.WineOclocK.spring.wine.dto.SearchWineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WineService {

    private final WineRepository wineRepository;
    private final UserRepository userRepository;

    /**
     * json 형식으로 fastApi 에게 보냄
     *
     * {
     *   "userId": "1",
     *   "result": " wine['wineType'] + ' ' + wine['wineSweet'] + '당도 ' + wine['wineBody'] + '바디 ' + wine['aroma1'] + ' ' + wine['aroma2'] + ' ' + wine['aroma3'] "
     * }
     */
    // 콘텐츠기반 추천 알고리즘 데이터 전처리
    public Map<String, String> recommendContent(User user) throws IOException {

        StringBuilder sb = new StringBuilder();

        //유저정보를 전처리화
        sb.append(user.getUserLikeType()).append(" ");

        // 와인당보 : 0 (단 와인 선호), 1(단 와인 불호), 2(상관없음)
        if (user.getUserLikeSweet() == 0){ sb.append("5당도").append(" "); }
        else if (user.getUserLikeSweet() == 1) { sb.append("1당도").append(" "); }
        else { sb.append("3당도").append(" "); }

        // 와인바디 : 0 (가벼운 와인 선호), 1 (무거운 와인 선호), 2 (상관없음)
        if (user.getUserLikeBody() == 0){ sb.append("1바디").append(" "); }
        else if (user.getUserLikeBody() == 1) { sb.append("5바디").append(" "); }
        else { sb.append("3바디").append(" ");; }

        sb.append(user.getUserLikeAroma1()).append(" ");
        sb.append(user.getUserLikeAroma2()).append(" ");
        sb.append(user.getUserLikeAroma3()).append(" ");

        Map<String, String> map = new HashMap<>();
        map.put("userWineStr", sb.toString());

        return map;
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

    public List<Wine> searchByFiltering(SearchReqDto searchReqDto) {
        Map<String, Object> searchKeys = new HashMap<>();

        if (searchReqDto.getType() != null) searchKeys.put("type", searchReqDto.getType());
        if (searchReqDto.getPrice() != 0) searchKeys.put("price", searchReqDto.getPrice());
        if (searchReqDto.getAroma() != null) searchKeys.put("aroma", searchReqDto.getAroma());

        return wineRepository.findAll(WineSpecification.searchWine(searchKeys))
                .stream().map(l -> new SearchReqDto((Wine) l))
                .collect(Collectors.toList());
    }
}
