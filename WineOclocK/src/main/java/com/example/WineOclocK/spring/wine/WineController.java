package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.*;
import com.example.WineOclocK.spring.user.UserService;
import com.example.WineOclocK.spring.wine.dto.NoteReqDto;
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

    /**
     * 와인 검색하기
     * (1)키워드 검색 및 (2)필터링 검색
     */
    @GetMapping("/search")
    public List<SearchWineDto> searchKeyword(@RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "keyword", required = false, defaultValue="") String keyword) {
        List<SearchWineDto> wineList = wineService.searchWines(userId, keyword);
        return wineList;
    }

    @PostMapping("/search/filtering")
    public List<SearchWineDto> searchFiltering(@RequestBody SearchReqDto searchReqDto) {
        return wineService.searchByFiltering(searchReqDto);
    }

    /**
     * 와인 추천하기
     */
    @GetMapping("/recommend/{userId}")
    public ResponseEntity<Map<String,Object>> requestToFlask (@PathVariable Long userId) throws IOException {

        System.out.println("---------------WineController.requestToFlask");
        System.out.println("---------------userId = " + userId);

        //0. Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON); //Json

        //1. URL set & 2. Body set
        String url;
        Map<String, Object> requestData;

        //2. 유저확인 및 requestData 만들기
        User user = userService.getUser(userId); //user 가져오기
        userService.updateUserRole(userId); // user rating 보고 level update 확인
        requestData = wineService.makeRecommendRequest(user);

        //3. 호출할 외부 API 를 입력 -> 각 유저 레벨 별로 다른 api 호출
        if (user.getRole() == Role.ROLE_USER_0) {
            url = "http://127.0.0.1:5000/recommend/content";
        } else if (user.getRole() == Role.ROLE_USER_1) {
            url = "http://127.0.0.1:5000/recommend/item";
        } else if (user.getRole() == Role.ROLE_USER_2) {
            url = "http://127.0.0.1:5000/recommend/latent";
        } else {
            url = "";
        }

        //4. 받은 데이터를 다시 보낼 수 있게 만들기
        JSONObject body = new JSONObject(requestData);

        // 설정한 Header + Body 를 가진 HttpEntity 객체 생성
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        // HttpEntity 로 API 서버에 Request 후 response 로 응답받기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestMessage, String.class);

        // 결과값을 담을 객체를 생성
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("statusCode", responseEntity.getStatusCodeValue()); // HTTP Status Code

        // flask response parsing
        List<Wine> wineList = wineService.flaskResponseParsing(user, responseEntity.getBody());

        //결과로 받은 와인 점수주기
        for(int i=0; i<wineList.size(); i++){
            wineService.insertRating(userId, wineList.get(i).getId(),3);
        }
        System.out.println("================결과로 받은 와인 점수를 줬어요!");
        resultMap.put("body", wineList); // 반환받은 실제 데이터 정보

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    /**
     * 와인 저장하기
     */
    @GetMapping("/saveWine/{userId}/{wineId}")
    public String insertSaveWine (@PathVariable Long userId, @PathVariable Long wineId) throws IOException {
        wineService.insertSave(userId, wineId);
        wineService.insertRating(userId, wineId,4); //와인 저장시 -> rating 점수 3점 추가
        return "와인 저장 완료!";
    }

    @DeleteMapping("/saveWine/{userId}/{wineId}")
    public String deleteSaveWine (@PathVariable Long userId, @PathVariable Long wineId) throws IOException {
        wineService.deleteSave(userId, wineId);
        return "와인 저장 취소 완료!";
    }

    /**
     * 와인 디테일 페이지
     * 와인정보 + 저장정보 + 테이스팅 노트 정보
     */
    @GetMapping("/detail/{userId}/{wineId}")
    public Map<String,Object> getDetail (@PathVariable Long userId, @PathVariable Long wineId) throws IOException {

        wineService.insertRating(userId, wineId,2); //* rating 추가 (디테일페이지 접근시 -> 2점)

        HashMap<String, Object> detailMap = new HashMap<>();
        Wine wine = wineService.getWine(wineId);

        detailMap.put("wineData", wine);
        detailMap.put("wineSave", wineService.existSave(userId, wineId));

        if(wineService.existNote(userId, wineId)) {
            detailMap.put("tastingNote", wineService.getNote(userId, wineId));
            detailMap.put("existNote", true);
        } else {
            detailMap.put("tastingNote", null);
            detailMap.put("existNote", false);
        }
        return detailMap;
    }

    @PostMapping("/detail/{userId}/{wineId}")
    public Note insertNote (@PathVariable Long userId, @PathVariable Long wineId, @RequestBody NoteReqDto noteReqDto) throws IOException {
        //* rating 추가 (테이스팅 노트 -> user 가 입력한 grade)
        wineService.insertRating(userId, wineId, noteReqDto.getGrade());
        return wineService.insertNote(userId, wineId,noteReqDto);
    }

    @PutMapping("/detail/{userId}/{wineId}")
    public Note updateNote (@PathVariable Long userId, @PathVariable Long wineId, @RequestBody NoteReqDto noteReqDto) throws IOException {
        //테이스팅 노트 접근 -> grade 추가
        wineService.insertRating(userId, wineId, noteReqDto.getGrade());
        return wineService.updateNote(userId, wineId, noteReqDto);
    }

    @DeleteMapping("/detail/{userId}/{wineId}")
    public Long DeleteNote (@PathVariable Long userId, @PathVariable Long wineId) throws IOException {
        return wineService.deleteNote(userId, wineId);
    }

    @GetMapping("/test/rating/{userId}")
    public String getHighRating(@PathVariable Long userId) throws IOException{
        return wineService.searchHighRating(userId);
    }
}
