//package com.example.WineOclocK.spring.wine;
//
//import org.springframework.stereotype.Service;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//@Service
//public class WineService {
//
//    // data.json 파일을 전부 읽어서 String 으로 반환
//    public String getCrawling() throws IOException {
//        return readLines("data.json");
//    }
//
//    // recent_data.json 파일을 전부 읽어서 String 으로 반환
//    public String getCrawlingRecent() throws IOException {
//        return readLines("recent_data.json");
//    }
//
//    // fileName 의 파일 내용을 전부 String 으로 읽어서 반환
//    private String readLines(String fileName) {
//        return new BufferedReader(
//                new InputStreamReader(
//                        // 지정한 fileName 을 resources 디렉토리에서 읽어 옴
//                        Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(fileName))
//                        //this.getClass().getClassLoader().getResourceAsStream(fileName)
//                )
//        ).lines().collect(Collectors.joining());
//    }
//}
