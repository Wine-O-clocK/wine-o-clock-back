package com.example.WineOclocK.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); //내 서버가 응답할 때 json 자바스크립트 허용
        config.addAllowedOriginPattern("*"); //포트번호 응답 다름 허용
        config.addAllowedHeader("*"); //헤더 값 응답 허용
        config.addAllowedMethod("*"); //메서드 응답 허용(get/post 등)
        config.addExposedHeader("Authorization"); // temp 추가

        //모든 url에 대하여 위 들을 적용시키겠다
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);

    }
}
