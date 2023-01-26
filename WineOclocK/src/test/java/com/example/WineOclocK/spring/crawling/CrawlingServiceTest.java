package com.example.WineOclocK.spring.crawling;

import com.example.WineOclocK.spring.crawling.entity.Access;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;

class CrawlingServiceTest {

    @Test
    void parsingJson() throws IOException, ParseException {

        ClassPathResource resource = new ClassPathResource("data.json");
        JSONObject json = (JSONObject) new JSONParser().parse(new InputStreamReader(resource.getInputStream(), "UTF-8")); //json-simple

        JSONArray mentionArr = (JSONArray) json.get("mention");
        JSONArray accessArr = (JSONArray) json.get("access");
        JSONArray presentArr = (JSONArray) json.get("present");
        JSONArray priceArr = (JSONArray) json.get("price");

//        System.out.println("CrawlingServiceTest.parsingJson");
//        System.out.println("mentionArr = " + mentionArr);
//        System.out.println("accessArr = " + accessArr);
//        System.out.println("presentArr = " + presentArr);
//        System.out.println("priceArr = " + priceArr);

        for (Object object : accessArr) {
            //System.out.println("for문 시작합니다");
            json = (JSONObject) object;

            String wineImg = (String) json.get("wineImage");
            //System.out.println("wineImg = " + wineImg);

            String wineName = (String) json.get("wineName");
            //System.out.println("CrawlingServiceTest.parsingJson");

            String wineNameEng = (String) json.get("wineNameEng");;
            //System.out.println("wineNameEng = " + wineNameEng);

            String wineType = (String) json.get("wineType");
            //System.out.println("wineType = " + wineType);

            int wineSweet = Integer.parseInt(String.valueOf(json.get("wineSweet"))) ;
            //System.out.println("wineSweet = " + wineSweet);

            int wineBody = Integer.parseInt(String.valueOf(json.get("wineBody")));
            //System.out.println("wineBody = " + wineBody);

            String wineVariety = (String) json.get("wineVariety");
            //System.out.println("wineVariety = " + wineVariety);

            //System.out.println("객체 생성합니다");

            Access access = Access.builder()
                    .wineImg(wineImg)
                    .wineName(wineName)
                    .wineNameEng(wineNameEng)
                    .wineType(wineType)
                    .wineSweet(wineSweet)
                    .wineBody(wineBody)
                    .wineVariety(wineVariety)
                    .build();
            System.out.println(access.getWineName());
        }
    }
}