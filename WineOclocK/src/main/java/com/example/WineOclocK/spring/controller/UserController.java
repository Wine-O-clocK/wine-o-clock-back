package com.example.WineOclocK.spring.controller;

import com.example.WineOclocK.spring.domain.entity.Role;
import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {
    //private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 로그인
     * @return
     */
    @GetMapping("/loginForm")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        return "login";
    }


    @GetMapping("/joinForm")
    public String joinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute User user) {
        user.setRole(Role.ROLE_USER);

        String encodePwd = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePwd);

        userRepository.save(user);  //반드시 패스워드 암호화해야함
        return "redirect:/login";
    }
}
