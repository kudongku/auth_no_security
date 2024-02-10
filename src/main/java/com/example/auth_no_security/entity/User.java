package com.example.auth_no_security.entity;

import com.example.auth_no_security.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends Timestamped {
    // TODO: 2/10/24 data annotation 수정
    // TODO: 2/10/24 validation 설정

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    public User(SignupRequestDto signupRequestDto, String encodedPassword) {
        this.username = signupRequestDto.getUsername();
        this.password = encodedPassword;
        this.email = signupRequestDto.getEmail();
    }
}
