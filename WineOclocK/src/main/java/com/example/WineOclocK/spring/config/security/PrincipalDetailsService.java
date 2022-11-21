package com.example.WineOclocK.spring.config.security;

import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("email :: {}",email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        log.info("LOAD USER BY USERNAME = USER : {}, {}",user.getEmail(), user.getPassword());

        return new PrincipalDetails(user);
    }
}

