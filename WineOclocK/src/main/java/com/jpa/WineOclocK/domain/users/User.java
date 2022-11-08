package com.jpa.WineOclocK.domain.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId; //내부 사용자 수 체크용

    private String email; //사용자 로그인 아이디
    private String pw; //사용자 패스워드
    private String username; //사용할 닉네임
    private String birthday; //해당 생일에 달에 생일 와인 추천

    @Builder

    public User(String email, String pw, String username, String birthday) {
        this.email = email;
        this.pw = pw;
        this.username = username;
        this.birthday = birthday;
    }
}
