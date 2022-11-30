package com.example.WineOclocK.spring.user;

import com.example.WineOclocK.spring.user.dto.JoinDto;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    // 회원가입 API
    @PostMapping("/join")
    public String join(@RequestBody JoinDto joinDto) {
        userService.join(joinDto);
        return "회원가입완료";
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserResponse> getUserFromToken(HttpServletRequest request) {
//        String name = (String) request.getAttribute("name");
//        User user = userService.findByName(name);
//        return ResponseEntity.ok().body(UserResponse.of(user));
//    }


//    @GetMapping("/me")
//    public ResponseEntity<MemberResponseDto> findMemberInfoById() {
//        return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
//    }

    // 로그인 API
//    @PostMapping("/login")
//    public String login(@RequestBody LoginDto loginDto) {
//        return userService.login(loginDto);
//    }
}
