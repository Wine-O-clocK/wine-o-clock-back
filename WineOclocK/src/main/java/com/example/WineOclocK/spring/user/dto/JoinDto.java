package com.example.WineOclocK.spring.user.dto;

import com.example.WineOclocK.spring.domain.entity.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class JoinDto {
    //(0)회원 기본 정보
    private String email; //사용자 로그인 아이디
    private String password; //사용자 패스워드

    //(1)회원 추가 정보
    private String username; //사용할 닉네임
    private String birthday; //해당 생일에 달에 생일 와인 추천 (19991002)

    //(2)회원 와인 선호도
    //private List<String> userLikeType;
    private String userLikeType; //레드, 화이트, 로제, 스파클링
    private int userLikeSweet; // 0(단 와인 좋아함) 1(단 와인 싫어함) 2(상관없음)
    private int userLikeBody;  //0(가벼운 와인 선호) 1(무거운 와인 선호) 2(상관없음)

    private String userLikeAroma1;
    private String userLikeAroma2;
    private String userLikeAroma3;
}
