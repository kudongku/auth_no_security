package com.example.auth_no_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncoderConfig {
    @Bean
    public Encoder encoder(){
        return new Encoder();
    }
}
