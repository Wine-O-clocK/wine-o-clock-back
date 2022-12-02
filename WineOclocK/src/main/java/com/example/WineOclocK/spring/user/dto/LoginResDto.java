package com.example.WineOclocK.spring.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDto {
    private String token;
    private String email;
    private String userName;
}
