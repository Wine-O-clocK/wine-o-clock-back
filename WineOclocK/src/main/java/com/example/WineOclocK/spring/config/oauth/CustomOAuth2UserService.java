package com.example.WineOclocK.spring.config.oauth;

import com.example.WineOclocK.spring.config.oauth.dto.OAuthAttributes;
import com.example.WineOclocK.spring.config.oauth.dto.SessionUser;
import com.example.WineOclocK.spring.user.social.SocialUser;
import com.example.WineOclocK.spring.user.social.SocialUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

//위에서 oauth2Login 이 성공한 이후 과정은 customOAuth2UserService 에서 진행
//OAuthAttributes 를 기반으로 가입 및 정보수정, 세션 저장 등 기능 수행
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final SocialUserRepository socialUserRepository;
    private final HttpSession httpSession;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //현재 로그인 진행 중인 서비스를 구분하는 코드
        //네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //OAuth2 로그인 진행 시 키가 되는 필드값을 이야기합니다. Primary Key 와 같은 의미입니다.
        //구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본 지원하지 않습니다. 구글의 기본 코드는 "sub"입니다.
        // oauth2 로그인 진행 시 키가 되는 필드 값
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuthAttributes: attribute 를 담을 클래스 (개발자가 생성
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        SocialUser user = saveOrUpdate(attributes);
        //SessionUser: 세션에 사용자 정보를 저장하기 위한 DTO 클래스 (개발자가 생성)
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                oAuth2User.getAttributes(),
//                userNameAttributeName);

                Collections.singleton(new SimpleGrantedAuthority("ROLE_GUEST")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private SocialUser saveOrUpdate(OAuthAttributes attributes) {
        SocialUser user = socialUserRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),attributes.getPicture()))
                .orElse(attributes.toEntity());

        return socialUserRepository.save(user);
    }
}
