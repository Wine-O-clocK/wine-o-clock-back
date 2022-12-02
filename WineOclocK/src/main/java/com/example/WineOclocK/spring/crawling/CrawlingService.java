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

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final AccessRepository accessRepository;
    private final MentionRepository mentionRepository;
    private final PresentRepository presentRepository;
    private final PriceRepository priceRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //매달 초기화 진행 후 저장
    @PostConstruct
    public void init() {
        accessRepository.deleteAll();
        mentionRepository.deleteAll();
        presentRepository.deleteAll();
        priceRepository.deleteAll();
    }

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
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

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
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

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
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        logger.info("★★★★★★★★★★★★★★★★ Price ★★★★★★★★★★★★★★★★");
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
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        logger.info("data.json -> db 저장 성공!");

        //String SUCCESS_MENTION = "data.json -> db 저장 성공!";
    }

    public Map<String, Object> createResponse() {

        List<Access> accessList = accessRepository.findAll();
        List<Mention> mentionList = mentionRepository.findAll();
        List<Present> presentList = presentRepository.findAll();
        List<Price> priceList = priceRepository.findAll();

        Map<String, Object> crawlingResponse = new HashMap<>();
        crawlingResponse.put("access", accessList);
        crawlingResponse.put("mention", mentionList);
        crawlingResponse.put("present", presentList);
        crawlingResponse.put("price", priceList);

        return crawlingResponse;
    }
}
