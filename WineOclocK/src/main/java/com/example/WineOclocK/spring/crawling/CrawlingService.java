package com.example.WineOclocK.spring.crawling;

import com.example.WineOclocK.spring.crawling.repository.*;
import com.example.WineOclocK.spring.crawling.entity.*;
import com.example.WineOclocK.spring.wine.repository.WineRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final WineRepository wineRepository;
    private final AccessRepository accessRepository;
    private final MentionRepository mentionRepository;
    private final PresentRepository presentRepository;
    private final PriceRepository priceRepository;
    private final RecentRepository recentRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //매달 초기화 진행 후 저장
    @PostConstruct
    public void init() {
        accessRepository.deleteAll();
        mentionRepository.deleteAll();
        presentRepository.deleteAll();
        priceRepository.deleteAll();
        recentRepository.deleteAll();
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
                    .wineImage((String) json.get("wineImage"))
                    .wineName((String) json.get("wineName"))
                    .wineNameEng((String) json.get("wineNameEng"))
                    .wineType((String) json.get("wineType"))
                    .winePrice(Integer.parseInt(String.valueOf(json.get("winePrice"))))
                    .wineSweet(Integer.parseInt(String.valueOf(json.get("wineSweet"))))
                    .wineBody(Integer.parseInt(String.valueOf(json.get("wineBody"))))
                    .wineVariety((String) json.get("wineVariety"))
                    .aroma1((String) json.get(("aroma1")))
                    .aroma2((String) json.get(("aroma2")))
                    .aroma3((String) json.get(("aroma3")))
                    .build();
            mentionRepository.save(mention);
        }
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        logger.info("★★★★★★★★★★★★★★★ ACCESS ★★★★★★★★★★★★★★★");
        for (Object object : accessArr) {
            json = (JSONObject) object;
            Access access = Access.builder()
                    .wineImage((String) json.get("wineImage"))
                    .wineName((String) json.get("wineName"))
                    .wineNameEng((String) json.get("wineNameEng"))
                    .wineType((String) json.get("wineType"))
                    .winePrice(Integer.parseInt(String.valueOf(json.get("winePrice"))))
                    .wineSweet(Integer.parseInt(String.valueOf(json.get("wineSweet"))))
                    .wineBody(Integer.parseInt(String.valueOf(json.get("wineBody"))))
                    .wineVariety((String) json.get("wineVariety"))
                    .aroma1((String) json.get(("aroma1")))
                    .aroma2((String) json.get(("aroma2")))
                    .aroma3((String) json.get(("aroma3")))
                    .build();
            accessRepository.save(access);
        }
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        logger.info("★★★★★★★★★★★★★★★ PRESENT ★★★★★★★★★★★★★★★");
        for (Object object : presentArr) {
            json = (JSONObject) object;
            Present present = Present.builder()
                    .wineImage((String) json.get("wineImage"))
                    .wineName((String) json.get("wineName"))
                    .wineNameEng((String) json.get("wineNameEng"))
                    .wineType((String) json.get("wineType"))
                    .winePrice(Integer.parseInt(String.valueOf(json.get("winePrice"))))
                    .wineSweet(Integer.parseInt(String.valueOf(json.get("wineSweet"))))
                    .wineBody(Integer.parseInt(String.valueOf(json.get("wineBody"))))
                    .wineVariety((String) json.get("wineVariety"))
                    .aroma1((String) json.get(("aroma1")))
                    .aroma2((String) json.get(("aroma2")))
                    .aroma3((String) json.get(("aroma3")))
                    .build();
            presentRepository.save(present);
        }
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        logger.info("★★★★★★★★★★★★★★★★ Price ★★★★★★★★★★★★★★★★");
        for (Object object : priceArr) {
            json = (JSONObject) object;
            Price price = Price.builder()
                    .wineImage((String) json.get("wineImage"))
                    .wineName((String) json.get("wineName"))
                    .wineNameEng((String) json.get("wineNameEng"))
                    .wineType((String) json.get("wineType"))
                    .winePrice(Integer.parseInt(String.valueOf(json.get("winePrice"))))
                    .wineSweet(Integer.parseInt(String.valueOf(json.get("wineSweet"))))
                    .wineBody(Integer.parseInt(String.valueOf(json.get("wineBody"))))
                    .wineVariety((String) json.get("wineVariety"))
                    .aroma1((String) json.get(("aroma1")))
                    .aroma2((String) json.get(("aroma2")))
                    .aroma3((String) json.get(("aroma3")))
                    .build();
            priceRepository.save(price);
        }
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        // 최신 로컬 제이슨 파일 읽기
        resource = new ClassPathResource("recent_data.json");
        JSONArray recentArr = (JSONArray) new JSONParser().parse(new InputStreamReader(resource.getInputStream(), "UTF-8"));

        // jsonArr 에서 하나씩 JSONObject 로 cast 해서 사용
        logger.info("★★★★★★★★★★★★★★★ RECENT ★★★★★★★★★★★★★★★");
        if (recentArr.size() > 0){
            for(int i=0; i<recentArr.size(); i++){
                json = (JSONObject)recentArr.get(i);
                Recent recent = Recent.builder()
                        .wineId(findWineId((String) json.get("wineName")))
                        .wineImage((String) json.get("wineImage"))
                        .wineName((String) json.get("wineName"))
                        .wineNameEng((String) json.get("wineNameEng"))
                        .wineType((String) json.get("wineType"))
                        .winePrice(Integer.parseInt(String.valueOf(json.get("winePrice"))))
                        .wineSweet(Integer.parseInt(String.valueOf(json.get("wineSweet"))))
                        .wineBody(Integer.parseInt(String.valueOf(json.get("wineBody"))))
                        .wineVariety((String) json.get("wineVariety"))
                        .aroma1((String) json.get(("aroma1")))
                        .aroma2((String) json.get(("aroma2")))
                        .aroma3((String) json.get(("aroma3")))
                        .build();
                recentRepository.save(recent);
            }
        }
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
        logger.info("data.json -> db 저장 성공!");

        //String SUCCESS_MENTION = "data.json -> db 저장 성공!";
    }

    public Long findWineId (String wineName) {
        return wineRepository.findByWineName(wineName).get().getId();
    }

    public Map<String, Object> createResponse() {

        List<Access> accessList = accessRepository.findAll();
        List<Mention> mentionList = mentionRepository.findAll();
        List<Present> presentList = presentRepository.findAll();
        List<Price> priceList = priceRepository.findAll();
        List<Recent> recentList =  recentRepository.findAll();

        Map<String, Object> crawlingResponse = new HashMap<>();
        crawlingResponse.put("access", accessList);
        crawlingResponse.put("mention", mentionList);
        crawlingResponse.put("present", presentList);
        crawlingResponse.put("price", priceList);
        crawlingResponse.put("recent", recentList);

        return crawlingResponse;
    }


}
