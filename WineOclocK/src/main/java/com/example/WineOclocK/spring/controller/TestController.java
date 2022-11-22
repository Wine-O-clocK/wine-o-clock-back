package com.example.WineOclocK.spring.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @PostMapping("/test")
    public String test1() {
        return "<h1> post test 통과 </h1>";
    }

    @GetMapping("/test")
    public String test2() {
        return "<h1> get test 통과 </h1>";
    }

    @DeleteMapping("/test")
    public String test3() {
        return "<h1> delete test 통과 </h1>";
    }

    @PutMapping("/test")
    public String test4() {
        return "<h1> put test 통과 </h1>";
    }

    @PatchMapping("/test")
    public String test5() {
        return "<h1> patch test 통과 </h1>";
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!!";
    }
}
