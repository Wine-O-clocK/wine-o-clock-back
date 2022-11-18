package com.example.WineOclocK.spring.controller;

import com.example.WineOclocK.spring.config.oauth.LoginUser;
import com.example.WineOclocK.spring.config.oauth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


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
}
