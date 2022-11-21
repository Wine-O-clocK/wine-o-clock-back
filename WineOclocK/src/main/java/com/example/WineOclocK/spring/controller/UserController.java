package com.example.WineOclocK.spring.controller;

import com.example.WineOclocK.spring.config.security.JwtTokenProvider;
import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!!";
    }
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/join")
    public Long join(@RequestBody Map<String, String> user) {
        return userRepository.save(User.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .role("USER_ROLE") // 최초 가입시 USER 로 설정
                .build()).getUserId();
    }

    // 로그인
    @PostMapping("/loginCustom")
    public String login(@RequestBody Map<String, String> user) {

        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(member.getUsername(), member.getRole());
    }

    @GetMapping("/checkJWT")
    public String list(){
        //권한체크
        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        User user2 = (User) user.getPrincipal();
        return user.getAuthorities().toString()+" / "+user2.getEmail()+" / "+user2.getPassword();
    }
}
