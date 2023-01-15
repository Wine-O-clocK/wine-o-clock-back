package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Role;
import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.entity.Wine;
import com.example.WineOclocK.spring.user.UserService;
import com.example.WineOclocK.spring.wine.dto.SearchWineDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor //private final 붙은 변수에 자동으로 생성자 만들어줌
public class WineController {

    private final WineService wineService;
    private final UserService userService;

    @GetMapping("/search")
    public List<SearchWineDto> searchWine(@RequestParam(value = "keyword", required = false, defaultValue="") String keyword) {
        //System.out.println("keyword = " + keyword);
        List<SearchWineDto> wineList = wineService.searchWines(keyword);
        System.out.println("--------- wineSearch 성공");
        return wineList;
    }

    @GetMapping("/recommend/{userId}")
    public ResponseEntity<String> requestToFlask (@PathVariable Long userId) throws IOException {

        //0. Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON); //Json

        //1. URL set & 2. Body set
        String url;
        Map<String, String> recommendData;

        //3. 호출할 외부 API 를 입력 -> 각 유저 레벨 별로 다른 api 호출
        User user = userService.getUser(userId); //유저 확인
        if (user.getRole() == Role.ROLE_USER_0) {
            url = "http://127.0.0.1:8000/recommend/content";
            recommendData = wineService.recommendContent(user);

        } else if (user.getRole() == Role.ROLE_USER_1) {
            url = "http://127.0.0.1:8000/recommend/item";
            recommendData = wineService.recommendContent(user);

        } else if (user.getRole() == Role.ROLE_USER_2) {
            url = "http://127.0.0.1:8000/recommend/latent";
            recommendData = wineService.recommendContent(user);

        } else {
            url = "";
            recommendData = wineService.recommendContent(user);
        }

        //4. 받은 데이터를 다시 보낼 수 있게 만
        JSONObject body = new JSONObject(recommendData);

        // 설정한 Header + Body 를 가진 HttpEntity 객체 생성
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        // HttpEntity 로 API 서버에 Request 후 response 로 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestMessage, String.class);

        // 응답 확인
        System.out.println("response = " + responseEntity.getBody());
        System.out.println("response.getHeaders() = " + responseEntity.getHeaders());

//        // 결과값을 담을 객체를 생성
//        HashMap<String, Object> resultMap = new HashMap<String, Object>();
//        resultMap.put("statusCode", responseEntity.getStatusCodeValue()); // HTTP Status Code
//        resultMap.put("header", responseEntity.getHeaders()); // 헤더 정보
//        resultMap.put("body", responseEntity.getBody()); // 반환받은 실제 데이터 정보

        return new ResponseEntity<>(responseEntity.getBody(), HttpStatus.OK);
    }
}
