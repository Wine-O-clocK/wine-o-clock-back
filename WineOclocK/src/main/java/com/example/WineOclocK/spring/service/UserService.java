package com.example.WineOclocK.spring.service;

import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.repository.UserRepository;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public String login(@RequestBody Map<String, String> user) {

        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return null;
    }

    /**
     * 회원가입
     * @param user
     */
    @Transactional
    public void join(@ModelAttribute User user) {
        // 이메일 중복 확인

        //암호화
        try{
            String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        } catch (Exception ignored) {
            throw new IllegalArgumentException("암호화실패.");
        }

        try{
            userRepository.save(user);
        } catch (Exception exception) {
            throw new IllegalArgumentException("데이터베이스 잘못 저장");
        }

        return userRepository.save(User.builder().build());
    }
    /**
     * 회원정보 저장
     *
     * @param infoDto 회원정보가 들어있는 DTO
     * @return 저장되는 회원의 PK
     */
    public Long save(UserInfoDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        return userRepository.save(UserInfo.builder()
                .email(infoDto.getEmail())
                .auth(infoDto.getAuth())
                .password(infoDto.getPassword()).build()).getCode();
    }
}
