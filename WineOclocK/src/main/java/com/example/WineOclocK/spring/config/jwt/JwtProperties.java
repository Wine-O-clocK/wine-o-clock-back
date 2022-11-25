package com.example.WineOclocK.spring.config.jwt;

public interface JwtProperties {
    String SECRET = "WineOclocK";
    Long EXPIRATION_TIME = 60 * 60 * 1000L; // 30분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
