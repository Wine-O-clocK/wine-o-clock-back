package com.example.WineOclocK.spring.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 200대
    SUCCESS(HttpStatus.OK, "OK"),

    // 400대
    NOT_SUPPORTED_HTTP_METHOD(HttpStatus.BAD_REQUEST,"지원하지 않는 Http Method 방식입니다."),
    NOT_VALID_METHOD_ARGUMENT(HttpStatus.BAD_REQUEST,"유효하지 않은 Request Body 혹은 Argument입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 유저를 찾을 수 없습니다."),
    WINE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 와인을 찾을 수 없습니다."),

    // 500대
    INTERNAL_SERVER_ERROR(HttpStatus.BAD_REQUEST, "서버 에러입니다");

    private final HttpStatus status;
    private final String message;
}
