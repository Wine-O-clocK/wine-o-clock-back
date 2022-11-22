package com.example.WineOclocK.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @PostMapping("/test")
    public String test() {
        return "<h1> test 통과 </h1>";
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!!";
    }
}
