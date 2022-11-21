package com.example.WineOclocK.spring.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 *
 * 토큰을 생성하고 검증하는 클래스
 * 해당 컴포넌트는 필터 클래스에서 사전 검증을 거침
 */

@RequiredArgsConstructor
@Component
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈

    @Value("spring.jwt.secret")
    private String secretKey;
    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {  // 객체 초기화, secretKey 를 Base64로 인코딩한다.
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 토큰 생성
    public String createToken(String userPk, List<String> roles) {
        // JWT payload 에 저장되는 정보단위, 보통 여기서 user 를 식별하는 값을 넣음
        Claims claims = Jwts.claims().setSubject(userPk);
        // 정보는 key / value 쌍으로 저장
        claims.put("roles", roles);
        Date now = new Date();

        //토큰 유효 시간
        long tokenValidTime = 60 * 60 * 1000L;
        return Jwts.builder()
                .setClaims(claims) // 데이터 저장
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, signature 에 들어갈 secret 값 세팅
                .compact();
    }

    // Jwt 토큰으로 인증 정보를 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 파싱 : "Authorization : jwt 토큰 값"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}