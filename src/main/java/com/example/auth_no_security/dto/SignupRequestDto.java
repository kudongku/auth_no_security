package com.example.auth_no_security.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private String emailVerifyKey;
}
