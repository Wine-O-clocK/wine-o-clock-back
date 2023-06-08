package com.example.WineOclocK.spring.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponse<T> {
    private Boolean isSuccess;
    private T data;
    private String message;

    private BaseResponse(boolean success, T data, String message){
        this.isSuccess = success;
        this.data = data;
        this.message = message;
    }
    //성공적으로 데이터를 반환할때
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, data, null);
    }
    //예외발생
    public static <T> BaseResponse<?> error(String message){
        return new BaseResponse<>(false, null, message);
    }
}
