package com.github.olson1998.synthwave.service.authorizationserver.application.user.config;

import com.github.olson1998.synthwave.service.authorizationserver.application.user.props.Argon2PasswordEncoderProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Argon2PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder(Argon2PasswordEncoderProperties argon2PasswordEncoderProperties) {
        return new Argon2PasswordEncoder(
                argon2PasswordEncoderProperties.getSaltLength(),
                argon2PasswordEncoderProperties.getHashLength(),
                argon2PasswordEncoderProperties.getParallelism(),
                argon2PasswordEncoderProperties.getMemory(),
                argon2PasswordEncoderProperties.getIterations()
        );
    }
}
