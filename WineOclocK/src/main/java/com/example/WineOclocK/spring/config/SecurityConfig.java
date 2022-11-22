package com.example.WineOclocK.spring.config;

import com.example.WineOclocK.spring.config.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring Security 설정 클래스
 */
@Configuration
@RequiredArgsConstructor
//@AllArgsConstructor
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터 체인에 등록!
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService; //로그인 후 액션 커스텀

    // authenticationManager 를 Bean 등록
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean //비밀번호를 암호화. controller 에 빈으로 등록된 BCryptPasswordEncoder 를 자동 주입
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
                    .antMatchers("/","/login/oauth2","/login", "/join", "/user", "/test").permitAll()
                    // USER 만 접근 가능한 URL
                    .antMatchers("/test/user").access("hasRole('ROLE_USER')")
                    // ADMIN 만 접근 가능한 URL
                    .antMatchers("/test/admin").access("hasRole('ROLE_ADMIN')")
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
                    .oauth2Login()                                          // oauth2Login 설정 시작
                    .defaultSuccessUrl("/")			                        // 로그인 성공하면 "/" 으로 이동
                    .failureUrl("/loginForm")		    // 로그인 실패 시 /loginForm 으로 이동
                    .userInfoEndpoint()                                     // oauth2Login 성공 이후의 설정을 시작
                    .userService(customOAuth2UserService); // SNS 로그인이 완료된 뒤 후처리가 필요함. 엑세스토큰 + 사용자프로필 정보

        // JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 전에 넣는다
        //http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
