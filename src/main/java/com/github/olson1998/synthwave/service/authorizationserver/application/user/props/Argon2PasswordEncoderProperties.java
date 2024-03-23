package com.github.olson1998.synthwave.service.authorizationserver.application.user.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "synthwave.service.authorizationserver.user.password.argon2")
public class Argon2PasswordEncoderProperties {

    private int saltLength = 128;

    private int hashLength = 128;

    private int parallelism = 2;

    private int memory = 1024;

    private int iterations = 2;
}
