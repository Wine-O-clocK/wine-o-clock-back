package com.example.WineOclocK.spring.crawling;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CrawlingController {
    private final CrawlingService crawlingService;

//    @GetMapping("/model")
//    public String modelTest(@RequestBody Crawling crawling){
//        System.out.println(crawling.getPresent());
//        System.out.println(crawling.getMention());
//        return crawling.toString();
//    }
    @GetMapping("/crawling")
    public Map<String, Object> jsonSave() throws ParseException, IOException {
        crawlingService.parsingJson();
        return crawlingService.createResponse();
    }

//    private List<String> mention;   // 한달 가장 언급 많은 순위
//    private List<String> access;    // 접근성 순위
//    private List<String> present;   // 선물 순위
//    private List<String> price;     // 가성비 순위
}
