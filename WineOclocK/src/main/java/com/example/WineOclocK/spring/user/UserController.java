package com.example.WineOclocK.spring.user;

import com.example.WineOclocK.spring.domain.entity.User;
import com.example.WineOclocK.spring.domain.entity.Wine;
import com.example.WineOclocK.spring.user.dto.JoinDto;
import com.example.WineOclocK.spring.user.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

    @GetMapping("/mypage/{userId}")
    public Map<String,Object> mypage(@PathVariable Long userId){
        HashMap<String, Object> map = new HashMap<>();

        //유저정보
        User user = userService.getUser(userId);
        map.put("user", user);

        //저장한 와인
        List<Wine> wineBySave= userService.getWineBySave(userId);
        map.put("wineBySave", wineBySave);

        //평가한 와인
        List<Wine> wineByNote = userService.getWineByNote(userId);
        map.put("wineByNote", wineByNote);

        return map;
    }

    @PutMapping("/mypage/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody JoinDto joinDto){
        return userService.updateUser(userId, joinDto);
    }


    @DeleteMapping("/mypage/{userId}")
    public String userDelete(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "회원탈퇴완료";
    }
}
