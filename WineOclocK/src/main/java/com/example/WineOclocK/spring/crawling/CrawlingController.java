package com.example.WineOclocK.spring.crawling;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CrawlingController {
    private final CrawlingService crawlingService;

    @GetMapping("/crawling")
    public Map<String, Object> jsonSave() throws ParseException, IOException {
        crawlingService.init();
        crawlingService.parsingJson();
        return crawlingService.createResponse();
    }
}
