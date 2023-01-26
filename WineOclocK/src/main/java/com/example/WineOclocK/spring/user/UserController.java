package com.example.WineOclocK.spring.user;

import com.example.WineOclocK.spring.user.dto.JoinDto;
import com.example.WineOclocK.spring.user.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입 API
    @PostMapping("/join")
    public String join(@RequestBody JoinDto joinDto) {
        userService.join(joinDto);
        return "회원가입완료";
    }

    @DeleteMapping("/mypage/{userId}")
    public String delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "회원탈퇴완료";
    }
}
