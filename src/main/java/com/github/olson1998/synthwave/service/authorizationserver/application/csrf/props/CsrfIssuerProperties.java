package com.github.olson1998.synthwave.service.authorizationserver.application.csrf.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nonnull;
import java.time.Duration;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "synthwave.service.authorizationserver.csrf")
@NoArgsConstructor
@AllArgsConstructor
public class CsrfIssuerProperties {

    @Nonnull
    private Duration expireDuration;

    @Nonnull
    private String secret;

}
