package com.example.WineOclocK.spring.controller;

import com.example.WineOclocK.spring.config.auth.LoginUser;
import com.example.WineOclocK.spring.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
//@Slf4j
public class IndexController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        if (user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }
}
