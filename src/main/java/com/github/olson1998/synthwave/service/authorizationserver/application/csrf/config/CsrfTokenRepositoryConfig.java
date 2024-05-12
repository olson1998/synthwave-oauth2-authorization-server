package com.github.olson1998.synthwave.service.authorizationserver.application.csrf.config;

import com.github.olson1998.synthwave.service.authorizationserver.application.csrf.props.CsrfIssuerProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.csrf.repository.ConfigurableCsrfTokenRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.csrf.CsrfTokenService;
import org.jasypt.util.binary.AES256BinaryEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsrfTokenRepositoryConfig {

    @Bean
    public ConfigurableCsrfTokenRepository csrfTokenRepository(@Value("${server.servlet.context-path:#{null}}") String servletContextPath,
                                                               CsrfIssuerProperties csrfIssuerProperties) {
        AES256BinaryEncryptor csrfPrivateSignatory = new AES256BinaryEncryptor();
        csrfPrivateSignatory.setPassword(csrfIssuerProperties.getSecret());
        return new CsrfTokenService(
                servletContextPath,
                csrfIssuerProperties.getExpireDuration(),
                csrfPrivateSignatory
        );
    }

}
