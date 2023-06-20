package com.example.WineOclocK.spring.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends Exception{
    //에러코드가 HttpStatus 와 message 포함
    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
