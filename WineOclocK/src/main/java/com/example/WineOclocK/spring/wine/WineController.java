package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.Role;
import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.entity.Wine;
import com.example.WineOclocK.spring.user.UserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@RequiredArgsConstructor //private final 붙은 변수에 자동으로 생성자 만들어줌
public class WineController {

    private final WineService wineService;
    private final UserService userService;


//    //테이블 리스트 가져오기
//    @GetMapping("/wines")
//    public List<Wine> list(){
//        return wineRepository.findAll();
//    }
//
//    @GetMapping("/")
//    public String wines(Model model) {
//        List<Wine> wines = wineRepository.findAll();
//        model.addAttribute("wines", wines);
//        return "basic/wines";
//    }

    @GetMapping("/recommend/{userId}")
    public ResponseEntity<String> recommendAI (@PathVariable Long userId) {
        //유저 확인 + 유저 레벨 반환
        User user = userService.getUser(userId);

        if (user.getRole() == Role.ROLE_USER_0) {
            URL url = new URL("http://localhost:8080/recommend/content");
        } else if (user.getRole() == Role.ROLE_USER_1) {
            URL url = new URL("http://localhost:8080/recommend/item");
        } else if (user.getRole() == Role.ROLE_USER_2) {
            URL url = new URL("http://localhost:8080/recommend/latent");

        }

        String responseMsg = "body_text";
        String result_txt = "";

        try {
            JSONObject reqParams = new JSONObject();
            reqParams.put("body_contents1", responseMsg); // body에 들어갈 내용을 담는다.

            URL url = new URL("https://www.test.com/test/open/order/possible-check"); // 호출할 외부 API 를 입력한다.

            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // header에 데이터 통신 방법을 지정한다.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");

            // Post인 경우 데이터를 OutputStream으로 넘겨 주겠다는 설정
            conn.setDoOutput(true);

            // Request body message에 전송
            OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
            os.write(reqParams.toString());
            os.flush();

            // 응답
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            JSONObject jsonObj = (JSONObject) JSONValue.parse(in.readLine());

            in.close();
            conn.disconnect();

            result_txt = "response :: " + jsonObj.get("result");
            System.out.println(result_txt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return result_txt;

        return new ResponseEntity<>(responseMsg, HttpStatus.OK);
    }
}
