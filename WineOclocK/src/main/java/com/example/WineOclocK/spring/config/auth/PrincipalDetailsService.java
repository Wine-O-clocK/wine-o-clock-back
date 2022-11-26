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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");
        logger.info("email :: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        logger.info("user :: {}", user);
        logger.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★");

        return new PrincipalDetails(user); // SecurityContext 의 Authertication 에 등록되어 인증정보를 가진다.
    }
}

