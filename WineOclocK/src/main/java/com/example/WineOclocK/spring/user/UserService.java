package com.example.WineOclocK.spring.user;

import com.example.WineOclocK.spring.domain.entity.Role;
import com.example.WineOclocK.spring.user.dto.JoinDto;
import com.example.WineOclocK.spring.user.dto.LoginDto;
import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

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
//        if(!userRepository.existsByEmail(joinDto.getEmail())){
//            throw new IllegalArgumentException("이미 존재하는 이메일 입니다");
//        }

        System.out.println(joinDto.getEmail());
        System.out.println(joinDto.getPassword());
        System.out.println(joinDto.getBirthday());
        System.out.println(joinDto.getUsername());

        System.out.println(joinDto.getUserLikeType());
        System.out.println(joinDto.getUserLikeSweet());
        System.out.println(joinDto.getUserLikeAroma1());
        System.out.println(joinDto.getUserLikeAroma2());
        System.out.println(joinDto.getUserLikeAroma3());
        System.out.println(userRepository);


        try {
            User user = User.builder()
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

                    .role(Role.ROLE_USER)
                    .build();

            userRepository.save(user);
        } catch (Exception exception) {
            throw new IllegalArgumentException("로그인 서비스 빌드 오류");
        }

    }

    public static String getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }
        return authentication.getName();
    }
}
