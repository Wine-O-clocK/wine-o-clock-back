package com.example.WineOclocK.spring.service;

import com.example.WineOclocK.spring.domain.dto.JoinDto;
import com.example.WineOclocK.spring.domain.dto.LoginDto;
import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일 입니다."));

        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        // 로그인에 성공하면 토큰 생성 후 반환
        return null;
    }

    /**
     * 회원가입
     */
    @Transactional
    public void join(JoinDto joinDto) {
        // 이메일 중복 확인
        if(!userRepository.existsByEmail(joinDto.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다");
        }

        try {
            User user = User.builder()
                    .userId(joinDto.getUserId())
                    .email(joinDto.getEmail())
                    .password(bCryptPasswordEncoder.encode(joinDto.getPassword())) //비밀번호 인코딩
                    .birthday(joinDto.getBirthday())
                    .username(joinDto.getUsername())

                    .userLikeType(joinDto.getUserLikeType())
                    .userLikeSweet(joinDto.getUserLikeSweet())
                    .userLikeBody(joinDto.getUserLikeBody())

                    .userLikeAroma1(joinDto.getUserLikeAroma1())
                    .userLikeAroma2(joinDto.getUserLikeAroma2())
                    .userLikeAroma3(joinDto.getUserLikeAroma3())
                    .role(joinDto.getRole())
                    .build();

            userRepository.save(user);
        } catch (Exception exception) {
            throw new IllegalArgumentException("로그인 서비스 빌드 오류");
        }

    }
//    /**
//     * 회원정보 저장
//     *
//     * @param infoDto 회원정보가 들어있는 DTO
//     * @return 저장되는 회원의 PK
//     */
//    public Long save(UserInfoDto infoDto) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        infoDto.setPassword(encoder.encode(infoDto.getPassword()));
//
//        return userRepository.save(UserInfo.builder()
//                .email(infoDto.getEmail())
//                .auth(infoDto.getAuth())
//                .password(infoDto.getPassword()).build()).getCode();
//    }
}
