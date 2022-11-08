package com.example.WineOclocK.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@Getter

public class User {

    private Integer userId; //내부 사용자 수 체크용

    private String email; //사용자 로그인 아이디
    private String pw; //사용자 패스워드
    private String username; //사용할 닉네임
    private String birthday; //해당 생일에 달에 생일 와인 추천

    public User(String email, String pw, String username, String birthday) {
        this.email = email;
        this.pw = pw;
        this.username = username;
        this.birthday = birthday;
    }
}
