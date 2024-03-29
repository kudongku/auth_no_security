package com.example.auth_no_security.controller;

import com.example.auth_no_security.dto.CommonResponseDto;
import com.example.auth_no_security.dto.LoginRequestDto;
import com.example.auth_no_security.dto.MailRequestDto;
import com.example.auth_no_security.dto.SignupRequestDto;
import com.example.auth_no_security.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/verifyMail")
    public ResponseEntity<CommonResponseDto> verifyMail(@RequestBody MailRequestDto mailRequestDto) throws NoSuchAlgorithmException {
        return userService.verifyMail(mailRequestDto);
    }
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) throws NoSuchAlgorithmException {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res) throws NoSuchAlgorithmException {
        return userService.login(loginRequestDto, res);
    }
}
