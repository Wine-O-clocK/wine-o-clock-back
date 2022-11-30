package com.example.WineOclocK.spring.config.auth;

import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;

    //로그인시에 DB에서 유저정보와 권한정보를 가져오게 됩니다.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        logger.info("★★★★★★★★★★★★★★[ PrincipalDetailsService] ★★★★★★★★★★★★★★★★★★★★★");
        logger.info(" 로그인 한 email :: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("데이터베이스에서 회원을 찾을 수 없음"));

        logger.info(" 로그인 한 user :: {}", user);
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        return new PrincipalDetails(user); // SecurityContext 의 Authertication 에 등록되어 인증정보를 가진다.
    }
}

