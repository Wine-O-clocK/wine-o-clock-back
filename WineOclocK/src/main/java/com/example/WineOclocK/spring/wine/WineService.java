package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.user.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WineService {

    private UserRepository userRepository;

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



    // recent_data.json 파일을 전부 읽어서 String 으로 반환
    public String recommend () throws IOException {
        return readLines("recent_data.json");
    }

    // fileName 의 파일 내용을 전부 String 으로 읽어서 반환
    private String readLines(String fileName) {
        return new BufferedReader(
                new InputStreamReader(
                        // 지정한 fileName 을 resources 디렉토리에서 읽어 옴
                        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(fileName))
                        //this.getClass().getClassLoader().getResourceAsStream(fileName)
                )
        ).lines().collect(Collectors.joining());
    }
}
