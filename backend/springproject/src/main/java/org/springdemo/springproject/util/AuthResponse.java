package org.springdemo.springproject.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String accessTokenExpiration;
    private String refreshTokenExpiration;
}
