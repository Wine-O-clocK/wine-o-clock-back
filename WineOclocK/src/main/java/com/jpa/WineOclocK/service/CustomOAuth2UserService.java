package com.jpa.WineOclocK.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.WineOclocK.config.auth.dto.OAuthAttributes;
import com.jpa.WineOclocK.config.auth.dto.SessionUser;
import com.jpa.WineOclocK.domain.entity.User;
import com.jpa.WineOclocK.domain.repository.UserRepository;
import lombok.SneakyThrows;
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

//위에서 oauth2Login이 성공한 이후 과정은 customOAuth2UserService에서 진행
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private UserRepository userRepository;
    private HttpSession httpSession;

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //현재 로그인 진행 중인 서비스를 구분하는 코드
        //네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용합
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //OAuth2 로그인 진행 시 키가 되는 필드값을 이야기합니다. Primary Key와 같은 의미입니다.
        //구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본 지원하지 않습니다. 구글의 기본 코드는 "sub"입니다.
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.
                of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        //objectMapper 를 이용해서 json 형식으로 출력해 보기 위해서 아래와 같이 구현합니다.
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userRequest));
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(oAuth2User));

        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                oAuth2User.getAttributes(),
//                userNameAttributeName);

                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
