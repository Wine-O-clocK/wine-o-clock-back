package com.example.WineOclocK.spring.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.WineOclocK.spring.config.auth.PrincipalDetails;
import com.example.WineOclocK.spring.domain.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/*
* 해당 클래스는 JwtTokenProvider 가 검증을 끝낸 JWT 로부터 유저 정보를 조회해와서
* UserPasswordAuthenticationFilter 로 전달
*/
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends GenericFilterBean {
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Override // Request 로 들어오는 Jwt Token 의 유효성을 검증하는 filter 를 filterChain 에 등록
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        // 헤더에서 JWT 를 받아옴
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//
//        // 유효한 토큰인지 확인
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//
//            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//
//            // SecurityContext 에 Authentication 객체를 저장합니다.
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        chain.doFilter(request, response);
//    }
//}

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AuthenticationManager authenticationManager;

    /**
     *  /login request 요청시 수행되는 메서드
     *  Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
     * @param request from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     * redirect as part of a multi-stage authentication process (such as OpenID).
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
        logger.info("JwtAuthenticationFilter : 진입");

        // request 에 있는 email 과 password 를 파싱해서 자바 Object 로 받기
        ObjectMapper om = new ObjectMapper();

        LoginDto loginDto = null;
        try {
            loginDto = om.readValue(request.getInputStream(), LoginDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //logger.debug("JwtAuthenticationFilter :: {}", loginDto);

        // 유저네임패스워드 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword());

        logger.debug("JwtAuthenticationFilter : 토큰생성완료");

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails 를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        //logger.debug("Authentication :: {}", principalDetails.getUser().getEmail());
        //logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        return authentication;
    }

    // JWT Token 생성해서 response 에 담아주기
    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response,
                                             FilterChain chain, Authentication authResult ) throws IOException, ServletException {
        //logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        //logger.debug("UserId :: {}", principalDetails.getUser().getUserId());
        //logger.debug("Email :: {}", principalDetails.getUser().getEmail());

        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getUser().getUserId())
                .withClaim("email", principalDetails.getUser().getEmail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        // Add token in response
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
    }

}