package com.example.WineOclocK.spring.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Spring Security 설정 클래스
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정들을 활성화, @EnableWebSecurity 는 모든 요청 URL 이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션이다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception  {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                .formLogin().disable()
                .httpBasic().disable();
        http
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "js/**", "/oauth-login").permitAll() // login URL 에는 누구나 접근 가능하게 합니다.
                .antMatchers("/특정_URL").hasRole("특정_ROLE")
                .anyRequest().authenticated() // 그 이외에는 인증된 사용자만 접근 가능하게 합니다.
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .oauth2Login() // oauth2Login 설정 시작
                    .userInfoEndpoint() // oauth2Login 성공 이후의 설정을 시작
                    .userService(customOAuth2UserService);
    }
}
