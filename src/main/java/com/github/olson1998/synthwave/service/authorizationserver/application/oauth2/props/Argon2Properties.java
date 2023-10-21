package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "synthwave.authorizationserver.argon2")
public class Argon2Properties {

    private int hashLength =16;

    private int saltLength = 32;

    private int parallelism = 1;

    private int memory = 16384;

    private int iterations = 2;
}
