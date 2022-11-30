package com.example.WineOclocK.spring.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

class JwtAuthenticationFilterTest {

    @Test
    void jwts_test() {
        String JWT_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3aW5lMkBzb29rbXl1bmcuYWMua3IiLCJpZCI6MiwiZXhwIjoxNjY5Nzk4NjYxLCJlbWFpbCI6IndpbmUyQHNvb2tteXVuZy5hYy5rciIsInVzZXJuYW1lIjoi7JmA7J247Jyg7KCAIn0.0j_9ujvzZ0JQUnzttf3-T9RSvMMn-Do3huTec87uNIQyKSqZiwbFgd-3RoVcH29ILV1niFbdTcOZdss17ffoow";
        try {
            JWT.decode(JWT_TOKEN);
        } catch (JWTDecodeException decodeEx) {
            System.out.println("Decode Error");
            decodeEx.printStackTrace();
        }

        DecodedJWT jwt = JWT.decode(JWT_TOKEN);

        System.out.println("jwt claims        : " + jwt.getClaims());
    }

}