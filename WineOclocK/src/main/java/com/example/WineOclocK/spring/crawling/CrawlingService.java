package com.example.WineOclocK.spring.crawling;

import com.example.WineOclocK.spring.crawling.repository.AccessRepository;
import com.example.WineOclocK.spring.crawling.repository.MentionRepository;
import com.example.WineOclocK.spring.crawling.repository.PresentRepository;
import com.example.WineOclocK.spring.crawling.repository.PriceRepository;
import com.example.WineOclocK.spring.crawling.response.Access;
import com.example.WineOclocK.spring.crawling.response.Mention;
import com.example.WineOclocK.spring.crawling.response.Present;
import com.example.WineOclocK.spring.crawling.response.Price;
import com.example.WineOclocK.spring.domain.entity.Wine;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final AccessRepository accessRepository;
    private final MentionRepository mentionRepository;
    private final PresentRepository presentRepository;
    private final PriceRepository priceRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // json 파싱하기
    @Transactional
    public void parsingJson() throws ParseException, IOException {

        logger.info("★★★★★★★★★★★★★★ [parsingJson] ★★★★★★★★★★★★★★★★★★★★★");

        // 로컬 제이슨 파일 읽기
        ClassPathResource resource = new ClassPathResource("data.json");
        JSONObject json = (JSONObject) new JSONParser().parse(new InputStreamReader(resource.getInputStream(), "UTF-8")); //json-simple

        logger.info("data.json :: {}", json);

        JSONArray mentionArr = (JSONArray) json.get("mention");
        JSONArray accessArr = (JSONArray) json.get("access");
        JSONArray presentArr = (JSONArray) json.get("present");
        JSONArray priceArr = (JSONArray) json.get("price");

        logger.info("★★★★★★★★★★★★★★★ MENTION ★★★★★★★★★★★★★★★");
        for (Object object : mentionArr) {
            json = (JSONObject) object;
            Mention mention = Mention.builder()
                    .wineImg((String) json.get("wineImage"))
                    .wineName((String) json.get("wineName"))
                    .wineNameEng((String) json.get("wineNameEng"))
                    .wineType((String) json.get("wineType"))
                    .wineSweet(Integer.parseInt(String.valueOf(json.get("wineSweet"))))
                    .wineBody(Integer.parseInt(String.valueOf(json.get("wineBody"))))
                    .wineVariety((String) json.get("wineVariety"))
                    .build();
            mentionRepository.save(mention);
        }
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        logger.info("★★★★★★★★★★★★★★★ ACCESS ★★★★★★★★★★★★★★★");
        for (Object object : accessArr) {
            json = (JSONObject) object;
            Access access = Access.builder()
                    .wineImg((String) json.get("wineImage"))
                    .wineName((String) json.get("wineName"))
                    .wineNameEng((String) json.get("wineNameEng"))
                    .wineType((String) json.get("wineType"))
                    .wineSweet(Integer.parseInt(String.valueOf(json.get("wineSweet"))))
                    .wineBody(Integer.parseInt(String.valueOf(json.get("wineBody"))))
                    .wineVariety((String) json.get("wineVariety"))
                    .build();
            Access accessResult = accessRepository.save(access);
        }
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        logger.info("★★★★★★★★★★★★★★★ PRESENT ★★★★★★★★★★★★★★★");
        for (Object object : presentArr) {
            json = (JSONObject) object;
            Present present = Present.builder()
                    .wineImg((String) json.get("wineImage"))
                    .wineName((String) json.get("wineName"))
                    .wineNameEng((String) json.get("wineNameEng"))
                    .wineType((String) json.get("wineType"))
                    .wineSweet(Integer.parseInt(String.valueOf(json.get("wineSweet"))))
                    .wineBody(Integer.parseInt(String.valueOf(json.get("wineBody"))))
                    .wineVariety((String) json.get("wineVariety"))
                    .build();
            presentRepository.save(present);
        }
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        logger.info("★★★★ ACCESS :: 순위 예상 - 다다, 미래, 퓨메, 도스, 도스 ★★★★★");
        for (Object object : priceArr) {
            json = (JSONObject) object;
            Price price = Price.builder()
                    .wineImg((String) json.get("wineImage"))
                    .wineName((String) json.get("wineName"))
                    .wineNameEng((String) json.get("wineNameEng"))
                    .wineType((String) json.get("wineType"))
                    .wineSweet(Integer.parseInt(String.valueOf(json.get("wineSweet"))))
                    .wineBody(Integer.parseInt(String.valueOf(json.get("wineBody"))))
                    .wineVariety((String) json.get("wineVariety"))
                    .build();
            Price priceResult = priceRepository.save(price);
        }
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        String SUCCESS_MENTION = "data.json -> db 저장 성공!";
    }

    //함수 만들려다 실패
//    private Object setJsonDto(Object object, Object classObj) {
//        logger.info("★★★★★★★★★★★★★★ Mention :: 순위 예상 - 다다, 미래, 퓨메, 도스, 도스 ★★★★★★★★★★★★★★★★★★★★★");
//        JSONObject json = (JSONObject) object;
//
//        String wineImg = (String) json.get("wineImage");
//        String wineName = (String) json.get("wineName");
//        String wineNameEng = (String) json.get("wineNameEng");;
//        String wineType = (String) json.get("wineType");
//        int wineSweet = Integer.parseInt(String.valueOf(json.get("wineSweet"))) ;
//        int wineBody = Integer.parseInt(String.valueOf(json.get("wineBody")));
//        String wineVariety = (String) json.get("wineVariety");
//
//        Access access = Access.builder()
//                .wineImg(wineImg)
//                .wineName(wineName)
//                .wineNameEng(wineNameEng)
//                .wineType(wineType)
//                .wineSweet(wineSweet)
//                .wineBody(wineBody)
//                .wineVariety(wineVariety)
//                .build();
//        logger.info("data.json :: {}", acc.getWineName());
//        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
//        return classObj;
//    }

}
