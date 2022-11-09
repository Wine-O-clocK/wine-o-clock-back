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
@Entity //테이블과 링크될 클래스 임을 나타냄 : 카멜케이스 이름 -> 언더스코어 네이밍으로 테이블 이름 매친
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId; //내부 사용자 수 체크용

    private String userEmail; //사용자 로그인 아이디
    private String userPw; //사용자 패스워드
    private String userName; //사용할 닉네임
    private String birthday; //해당 생일에 달에 생일 와인 추천

    @Builder
    public User(String email, String pw, String username, String birthday) {
        this.userEmail = email;
        this.userPw = pw;
        this.userName = username;
        this.birthday = birthday;
    }
}
