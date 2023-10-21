package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props.Argon2Properties;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public Argon2PasswordEncoder argon2PasswordEncoder(@NonNull Argon2Properties argon2Properties){
        return new Argon2PasswordEncoder(
                argon2Properties.getSaltLength(),
                argon2Properties.getHashLength(),
                argon2Properties.getParallelism(),
                argon2Properties.getMemory(),
                argon2Properties.getIterations()
        );
    }
}
