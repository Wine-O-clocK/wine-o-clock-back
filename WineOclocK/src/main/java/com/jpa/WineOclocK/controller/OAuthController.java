package com.jpa.WineOclocK.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class OAuthController {

    @GetMapping({"", "/"})
    public String getAuthorizationMessage() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/oauth-login")
    public String ouathLogin() {
        return "oauth-login";
    }

    @GetMapping({"/loginSuccess", "/hello"})
    public String loginSuccess() {
        return "hello";
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "loginFailure";
    }
}