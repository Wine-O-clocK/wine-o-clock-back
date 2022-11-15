package com.jpa.WineOclocK;

import com.jpa.WineOclocK.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class OAuthController {
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model){

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

//    @GetMapping("/oauth-login")
//    public String login() {
//        return "oauth-login";
//    }
//
//    @GetMapping({"/loginSuccess", "/hello"})
//    public String loginSuccess() {
//        return "hello";
//    }
//
//    @GetMapping("/loginFailure")
//    public String loginFailure() {
//        return "loginFailure";
//    }
}