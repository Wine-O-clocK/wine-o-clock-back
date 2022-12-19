package com.example.WineOclocK.spring.wine;

import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WineService {

    private UserRepository userRepository;

    // 콘텐츠기반 추천 알고리즘
    public String recommendContent(@RequestBody Long userId) throws IOException {
        //유저 id 받기 -> DB 에서 유저 정보 가져오기 -> 배열로 결과 가져오기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("가입하지 않은 사용자 입니다."));;

        ArrayList<String> userDataList = new ArrayList<>();

        //유저정보를 전처리화
        userDataList.add(user.getUserLikeType());
        if (user.getUserLikeSweet() == 0){ // 0 -> 단 와인을 좋아함 = 5
            userDataList.add("5당도");
        } else if (user.getUserLikeSweet() == 1) { //1 -> 단 와인을 싫어함 = 1
            userDataList.add("1당도");
        } else { //2 -> 상관없음 = 3
            userDataList.add("3당도");
        }

        if (user.getUserLikeBody() == 0){ //0 -> 가벼운 와인 선호 = 1
            userDataList.add("1바디");
        } else if (user.getUserLikeBody() == 1) { //1 -> 무거운 와인 선호 = 5
            userDataList.add("5바디");
        } else { //2 -> 상관없음 = 3
            userDataList.add("3바디");
        }

        userDataList.add(user.getUserLikeAroma1());
        userDataList.add(user.getUserLikeAroma2());
        userDataList.add(user.getUserLikeAroma3());

        //파이썬 함수 진행


        //
        return readLines("data.json");
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
