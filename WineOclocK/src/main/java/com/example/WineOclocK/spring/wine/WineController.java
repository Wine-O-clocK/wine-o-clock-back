package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Role;
import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.entity.Wine;
import com.example.WineOclocK.spring.user.UserService;
import com.example.WineOclocK.spring.wine.dto.SearchReqDto;
import com.example.WineOclocK.spring.wine.dto.SearchWineDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor //private final 붙은 변수에 자동으로 생성자 만들어줌
public class WineController {

    private final WineService wineService;
    private final UserService userService;

    @GetMapping("/search")
    public List<SearchWineDto> searchKeyword(@RequestParam(value = "keyword", required = false, defaultValue="") String keyword) {
        List<SearchWineDto> wineList = wineService.searchWines(keyword);
        System.out.println("--------- wineSearch 성공");
        return wineList;
    }

    @GetMapping("/search/filtering")
    public List<SearchWineDto> searchFiltering(@RequestBody SearchReqDto searchReqDto) {
        return wineService.searchByFiltering(searchReqDto);
    }

    @GetMapping("/recommend/{userId}")
    public ResponseEntity<Map<String,Object>> requestToFlask (@PathVariable Long userId) throws IOException {

        System.out.println("---------------WineController.requestToFlask");

        //0. Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON); //Json

        System.out.println("-------헤더 세팅 완료");

        //1. URL set & 2. Body set
        String url;
        Map<String, Object> recommendData;

        //3. 호출할 외부 API 를 입력 -> 각 유저 레벨 별로 다른 api 호출
        User user = userService.getUser(userId); //유저 확인
        System.out.println("-------user.getRole() = " + user.getRole());
        if (user.getRole() == Role.ROLE_USER_0) {
            url = "http://127.0.0.1:5000/recommend/content";
            recommendData = wineService.recommendContent(user);

            System.out.println("-------url = " + url);
            System.out.println("-------recommendData = " + recommendData.get("userWineStr"));
            System.out.println("-------userLikeType = " + recommendData.get("userLikeType"));
            System.out.println("-------userLikeSweet = " + recommendData.get("userLikeSweet"));
            System.out.println("-------userLikeBody = " + recommendData.get("userLikeBody"));
            System.out.println("-------userLikeAroma1 = " + recommendData.get("userLikeAroma1"));
            System.out.println("-------userLikeAroma2 = " + recommendData.get("userLikeAroma2"));
            System.out.println("-------userLikeAroma3 = " + recommendData.get("userLikeAroma3"));

        } else if (user.getRole() == Role.ROLE_USER_1) {
            url = "http://127.0.0.1:5000/recommend/item";
            recommendData = wineService.recommendItem();
            System.out.println("-------recommendData = " + recommendData.get("ratings"));

        } else if (user.getRole() == Role.ROLE_USER_2) {
            url = "http://127.0.0.1:5000/recommend/latent";
            recommendData = wineService.recommendContent(user);

        } else {
            url = "";
            recommendData = wineService.recommendContent(user);
        }

        //4. 받은 데이터를 다시 보낼 수 있게 만들기
        JSONObject body = new JSONObject(recommendData);

        // 설정한 Header + Body 를 가진 HttpEntity 객체 생성
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        // HttpEntity 로 API 서버에 Request 후 response 로 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestMessage, String.class);

        // 결과값을 담을 객체를 생성
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("statusCode", responseEntity.getStatusCodeValue()); // HTTP Status Code

        if (user.getRole() == Role.ROLE_USER_0) {
            // flask response parsing인
            List<Wine> wineList = wineService.flaskResponseParsing(responseEntity.getBody());
            resultMap.put("body", wineList); // 반환받은 실제 데이터 정보
        } else if (user.getRole() == Role.ROLE_USER_1) {
            System.out.println(responseEntity.getBody());
        } else if (user.getRole() == Role.ROLE_USER_2) {
            System.out.println(responseEntity.getBody());
        } else {

        }


        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
