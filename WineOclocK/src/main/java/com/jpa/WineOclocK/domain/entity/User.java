package com.jpa.WineOclocK.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity //테이블과 링크될 클래스 임을 나타냄 : 카멜케이스 이름 -> 언더스코어 네이밍으로 테이블 이름 매친
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; //내부 사용자 수 체크용

    //(0)회원 기본 정보
    @Column(length = 20, nullable = false)
    private String email; //사용자 로그인 아이디
    @Column(length = 100, nullable = false)
    private String password; //사용자 패스워드

    //(1)회원 추가 정보
    @Column(length = 10, nullable = false)
    private String username; //사용할 닉네임
    @Column(length = 10, nullable = false)
    private String birthday; //해당 생일에 달에 생일 와인 추천 (19991002)

    //(2)회원 와인 선호도
    @Column(length = 10, nullable = false)
    private String userLikeType; //레드, 화이트, 로제, 스파클링

    private int userLikeSweet; // 0(단 와인 좋아함) 1(단 와인 싫어함) 2(상관없음)
    private int userLikeBody;  //0(가벼운 와인 선호) 1(무거운 와인 선호) 2(상관없음)

    @Column(length = 10)
    private String userLikeAroma1;
    @Column(length = 10)
    private String userLikeAroma2;
    @Column(length = 10)
    private String userLikeAroma3;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.username = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    @Builder
    public User(String email, String password, String username, String birthday,
                String userLikeType, int userLikeSweet, int userLikeBody,
                String userLikeAroma1, String userLikeAroma2, String userLikeAroma3) {

        this.email = email;
        this.password = password;
        this.username = username;
        this.birthday = birthday;
        this.userLikeType = userLikeType;
        this.userLikeSweet = userLikeSweet;
        this.userLikeBody = userLikeBody;
        this.userLikeAroma1 = userLikeAroma1;
        this.userLikeAroma2 = userLikeAroma2;
        this.userLikeAroma3 = userLikeAroma3;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public User update(String name, String picture) {
        this.username = name;
        this.picture = picture;

        return this;
    }
}