package com.example.WineOclocK.spring.user;

import com.example.WineOclocK.spring.user.entity.User;
import com.example.WineOclocK.spring.wine.entity.Wine;
import com.example.WineOclocK.spring.user.dto.JoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 유저 레벨업 테스트
     */
    @GetMapping("test/updateRole/{userId}")
    public String updateRole (@PathVariable Long userId) throws IOException {
        userService.updateUserRole(userId);
        User user = userService.getUser(userId);
        String userRole = String.valueOf(user.getRole());
        return userRole;
    }

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
        User user = userService.updateUser(userId, joinDto);
        return user;
    }

    @DeleteMapping("/mypage/{userId}")
    public String userDelete(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "회원탈퇴완료";
    }

    @GetMapping("test/upRole/{userId}/{userLv}")
    public String upRole(@PathVariable Long userId, @PathVariable int userLv) throws IOException{
        return userService.upRole(userId, userLv);
    }
}
