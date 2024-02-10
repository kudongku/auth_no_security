package com.example.auth_no_security.service;

import com.example.auth_no_security.config.Encoder;
import com.example.auth_no_security.dto.CommonResponseDto;
import com.example.auth_no_security.dto.MailRequestDto;
import com.example.auth_no_security.dto.SignupRequestDto;
import com.example.auth_no_security.entity.Mail;
import com.example.auth_no_security.entity.User;
import com.example.auth_no_security.repository.MailRepository;
import com.example.auth_no_security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private static final String senderEmail = "kudongku@gmail.com";
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final MailRepository mailRepository;
    private final Encoder encoder;

    public ResponseEntity<CommonResponseDto> signup(SignupRequestDto signupRequestDto) throws NoSuchAlgorithmException {
        String username = signupRequestDto.getUsername();
        String encodedPassword = encoder.encrypt(signupRequestDto.getPassword());
        String email = signupRequestDto.getEmail();
        String emailVerifyKey = signupRequestDto.getEmailVerifyKey();

        if(userRepository.findByUsername(username).isPresent()){
            return ResponseEntity.status(400).body(new CommonResponseDto("동일한 아이디가 존재합니다.", 400));
        }

        if(userRepository.findByEmail(email).isPresent()){
            return ResponseEntity.status(400).body(new CommonResponseDto("동일한 이메일이 존재합니다.", 400));
        }

        Optional<Mail> mail = mailRepository.findByEmail(email);

        if(mail.isEmpty()){
            return ResponseEntity.status(400).body(new CommonResponseDto("메일 인증이 이루어지지 않았습니다.", 400));
        }else if(!mail.get().getEncodedEmail().equals(emailVerifyKey)){
            return ResponseEntity.status(400).body(new CommonResponseDto("인증키가 올바르지 않습니다..", 400));
        }

        userRepository.save(new User(signupRequestDto, encodedPassword));
        return ResponseEntity.status(200).body(new CommonResponseDto("회원가입 성공", 200));
    }

    public ResponseEntity<CommonResponseDto> verifyMail(MailRequestDto mailRequestDto) throws NoSuchAlgorithmException {
        String title = "[노스프링] 가입 인증 메일입니다.";
        String contentHeader = "인증번호는\n";
        String contentFooter = "\n입니다.";
        String encodedEmail = encoder.encrypt(mailRequestDto.getMail());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailRequestDto.getMail());
        message.setFrom(senderEmail);
        message.setSubject(title);
        message.setText(contentHeader + encodedEmail + contentFooter);

        javaMailSender.send(message);
        mailRepository.save(new Mail(mailRequestDto.getMail(), encodedEmail));
        return ResponseEntity.status(200).body(new CommonResponseDto("가입 이메일 전송 성공", 200));
    }
}
