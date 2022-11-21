package com.example.WineOclocK.spring.config.oauth;

import com.example.WineOclocK.spring.config.security.JwtAuthenticationFilter;
import com.example.WineOclocK.spring.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정 클래스
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정들을 활성화, @EnableWebSecurity 는 모든 요청 URL 이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션이다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService; //로그인 후 액션 커스텀

    // authenticationManager를 Bean 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
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
                // 모두 접근 가능한 URL
                .authorizeRequests()
                .antMatchers(
                        "/","/login/oauth2","/login", "/join", "/refresh",
                        "/swagger-ui.html", "/swagger/**", "/swagger-resources/**", "/webjars/**", "/v2/api-docs",
                        "/notice/**").permitAll()
                .and()
                    // USER만 접근 가능한 URLㄱ
                    .authorizeRequests()
                    .antMatchers("/test/user")
                    .authenticated()
                .and()
                    // ADMIN만 접근 가능한 URL
                    .authorizeRequests()
                    .antMatchers("/test/admin")
                    .hasRole("ADMIN")
                .and()
                    // 그 이외에는 인증된 사용자만 접근 가능하게 합니다.
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .oauth2Login() // oauth2Login 설정 시작
                    .userInfoEndpoint() // oauth2Login 성공 이후의 설정을 시작
                    .userService(customOAuth2UserService);

        // JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 전에 넣는다
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        // + 토큰에 저장된 유저정보를 활용하여야 하기 때문에 CustomUserDetailService 클래스를 생성 -> 세션을 사용하지 않기 위해
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
