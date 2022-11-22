package com.example.WineOclocK.spring.controller;

import com.example.WineOclocK.spring.config.oauth.LoginUser;
import com.example.WineOclocK.spring.config.oauth.dto.SessionUser;
import com.example.WineOclocK.spring.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequiredArgsConstructor
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if (user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }
    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/guest")
    public @ResponseBody String manager() {
        return "guest";
    }
}
