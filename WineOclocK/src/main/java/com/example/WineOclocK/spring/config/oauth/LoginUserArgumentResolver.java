package com.example.WineOclocK.spring.config.oauth;

import com.example.WineOclocK.spring.config.oauth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    /**
     * Controller 메서드가 특정 파라미터를 지원하는지 판단
     * 1) @LoginUser 어노테이션이 붙어있는지
     * 2) 파라미터 클래스타입 SessionUser.class인지
     *   ==>  1) && 2) ? true : false
     */
    //컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
    //@LoginUser 어노테이션이 붙어있는지 && 파라미터 클래스 타입이 SessionUser.class 인지
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    //파라미터에 전달할 객체 생성
    //세션에서 객체를 가져옴
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}
