package com.example.WineOclocK.spring.controller;

import com.example.WineOclocK.spring.domain.dto.MemberDto;
import com.example.WineOclocK.spring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    // 회원가입 API
    @PostMapping("/mem/join")
    public Long join(@RequestBody MemberDto memberDto) {
        return memberService.join(memberDto);
    }

    // 로그인 API
    @PostMapping("/mem/login")
    public String login(@RequestBody MemberDto memberDto) {
        return memberService.login(memberDto);
    }
}
