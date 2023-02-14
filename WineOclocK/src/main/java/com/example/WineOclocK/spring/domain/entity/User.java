package com.example.WineOclocK.spring.domain.entity;

import com.example.WineOclocK.spring.domain.entity.Role;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    //(0)회원 기본 정보
    @Column(length = 40, unique = true)
    private String email; //사용자 로그인 아이디

    @Column(length = 100, nullable = false)
    private String password; //사용자 패스워드

    //(1)회원 추가 정보
    @Column(length = 20, nullable = false)
    private String username; //사용할 닉네임
    @Column(length = 10, nullable = false)
    private String birthday; //해당 생일에 달에 생일 와인 추천 (19991002)

    //(2)회원 와인 선호도
    @Column(length = 50, nullable = false)
    private String userLikeType; //리스트를 받아 userService 에서 문자열 변경 ( 레드, 화이트, 로제, 스파클링 )

    private int userLikeSweet; // 0(단 와인 좋아함) 1(단 와인 싫어함) 2(상관없음)
    private int userLikeBody;  //0(가벼운 와인 선호) 1(무거운 와인 선호) 2(상관없음)

    @Column(length = 10)
    private String userLikeAroma1;

    @Column(length = 10)
    private String userLikeAroma2;

    @Column(length = 10)
    private String userLikeAroma3;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * update 기능
     */
    public void update(String password, String username, String birthday,
                       String userLikeType, int userLikeSweet, int userLikeBody,
                       String userLikeAroma1, String userLikeAroma2, String userLikeAroma3) {

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

    @Override
    public String toString() {
        return
                "email = " + email + " password = " + password + " role = " + role
                        + "\n username = " + username+ "birthday = " + birthday
                        + "\n" + "userLikeType = " + userLikeType
                        + "\n" + " userLikeSweet = " + userLikeSweet + " userLikeBody = " + userLikeBody
                        + "\n" + "userLikeAroma1 = " + userLikeAroma1 + " userLikeAroma2 = " + userLikeAroma2 + " userLikeAroma3 = " + userLikeAroma3 ;
    }
}