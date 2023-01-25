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

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public User getUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found user with id =" + id));
    }

    /**
     * 로그인
     */
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
        //이메일 중복 확인
        if(userRepository.existsByEmail(joinDto.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다");
        }

        //유저선호와인 타입 list -> str 로 변경작업
        List<String> userLikeTypeList = joinDto.getUserLikeType();
        StringBuilder sb = new StringBuilder();
        for (String wineType : userLikeTypeList) {
            sb.append(wineType).append(" ");
        }
        String userLikeTypeStr = sb.toString().substring(0, sb.length() - 1);


        // 와인당도 : 0 (단 와인 선호), 1(단 와인 불호), 2(상관없음)
        int userLikeSweetInt;
        if (joinDto.getUserLikeSweet() == 0){ userLikeSweetInt = 5; }
        else if (joinDto.getUserLikeSweet() == 1){ userLikeSweetInt = 1; }
        else { userLikeSweetInt = 3; }

        // 와인바디 : 0 (가벼운 와인 선호), 1 (무거운 와인 선호), 2 (상관없음)
        int userLikeBodyInt;
        if (joinDto.getUserLikeBody() == 0){ userLikeBodyInt = 1;}
        else if (joinDto.getUserLikeBody() == 1) { userLikeBodyInt = 5; }
        else { userLikeBodyInt = 3; }

        System.out.println("joinDto.getEmail() = " + joinDto.getEmail());
        System.out.println("joinDto.getPassword() = " + joinDto.getPassword());
        System.out.println("joinDto.getBirthday() = " + joinDto.getBirthday());
        System.out.println("joinDto.getUsername() = " + joinDto.getUsername());

        System.out.println("userLikeTypeStr = " + userLikeTypeStr);
        System.out.println("userLikeSweetInt = " + userLikeSweetInt);
        System.out.println("userLikeBodyInt = " + userLikeBodyInt);

        System.out.println("joinDto.getUserLikeAroma1() = " + joinDto.getUserLikeAroma1());
        System.out.println("joinDto.getUserLikeAroma2() = " + joinDto.getUserLikeAroma2());
        System.out.println("joinDto.getUserLikeAroma3() = " + joinDto.getUserLikeAroma3());

        try {
            User user = User.builder()
                    .email(joinDto.getEmail())
                    .password(bCryptPasswordEncoder.encode(joinDto.getPassword())) //비밀번호 인코딩
                    .birthday(joinDto.getBirthday())
                    .username(joinDto.getUsername())

                    .userLikeType(userLikeTypeStr)
                    .userLikeSweet(userLikeSweetInt)
                    .userLikeBody(userLikeBodyInt)

                    .userLikeAroma1(joinDto.getUserLikeAroma1())
                    .userLikeAroma2(joinDto.getUserLikeAroma2())
                    .userLikeAroma3(joinDto.getUserLikeAroma3())

                    .role(Role.ROLE_USER_1) // 신규가입하자마자 등록되는 레벨
                    .build();
            userRepository.save(user);
        } catch (Exception exception) {
            throw new IllegalArgumentException("회원가입 서비스 빌드 오류");
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

//
//        System.out.println(joinDto.getEmail());
//        System.out.println(joinDto.getPassword());
//        System.out.println(joinDto.getBirthday());
//        System.out.println(joinDto.getUsername());
//
//        System.out.println(joinDto.getUserLikeType());
//        System.out.println(joinDto.getUserLikeSweet());
//        System.out.println(joinDto.getUserLikeAroma1());
//        System.out.println(joinDto.getUserLikeAroma2());
//        System.out.println(joinDto.getUserLikeAroma3());
//        System.out.println(userRepository);
//
