package com.example.WineOclocK.spring.crawling;

import com.example.WineOclocK.spring.domain.entity.Crawling;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CrawlingController {
    //private final CrawlingRepository crawlingRepository;

    @GetMapping("/model")
    public String modelTest(@RequestBody Crawling crawling){
        System.out.println(crawling.getPresent());
        System.out.println(crawling.getMention());
        return crawling.toString();
    }

//    private List<String> mention;   // 한달 가장 언급 많은 순위
//    private List<String> access;    // 접근성 순위
//    private List<String> present;   // 선물 순위
//    private List<String> price;     // 가성비 순위
}
