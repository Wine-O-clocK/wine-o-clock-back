package com.example.WineOclocK.spring.config.oauth.dto;

import com.example.WineOclocK.spring.user.social.SocialUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 세션에 저장하려면 직렬화를 해야 하는데
 * User 엔티티는 추후 변경사항이 있을 수 있기 때문에
 * 직렬화를 하기 위한 별도의 SessionUser 클래스 생성
 */
@Getter
@NoArgsConstructor
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(SocialUser user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}