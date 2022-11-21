package com.example.WineOclocK.spring.config.oauth;

import com.example.WineOclocK.spring.config.security.JwtAuthenticationFilter;
import com.example.WineOclocK.spring.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    // authenticationManager 를 Bean 등록
    @Bean
    @Override //비밀번호를 암호화. controller 에 빈으로 등록된 BCryptPasswordEncoder 를 자동 주입
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override //인증을 무시할 경로 설정
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override // http 관련 인증 설정
    protected void configure(HttpSecurity http) throws Exception  {
        http
                // + 토큰에 저장된 유저정보를 활용하여야 하기 때문에 CustomUserDetailService 클래스를 생성 -> 세션을 사용하지 않기 위해
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable();

        http.authorizeRequests()
                    // 모두 접근 가능한 URL
                    .antMatchers("/","/login/oauth2","/login", "/join", "/user").permitAll()
                    // USER 만 접근 가능한 URL
                    .antMatchers("/test/user").authenticated()
                    // ADMIN 만 접근 가능한 URL
                    .antMatchers("/test/admin").hasRole("ROLE_ADMIN")
                    // 그 이외에는 인증된 사용자만 접근 가능
                    .anyRequest().authenticated()

                .and()
                    .formLogin() // 로그인 설정 시작
                    .loginPage("/login") // 로그인 페이지 링크
                    .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true) // 로그아웃 이후 세션 전체 삭제 여부
                .and()
                    .oauth2Login() // oauth2Login 설정 시작
                    .userInfoEndpoint() // oauth2Login 성공 이후의 설정을 시작
                    .userService(customOAuth2UserService);

        // JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 전에 넣는다
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
