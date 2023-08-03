package com.example.WineOclocK.spring.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends Exception{
    //에러코드가 HttpStatus 와 message 포함
    private ResponseCode code;

    public BaseException(ResponseCode code) {
        super(code.getMessage());
        this.code = code;
    }
}
