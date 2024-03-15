package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props.OAuth2AuthorizationServerProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RegisteredClientDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.OAuth2AuthorizationRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.OAuth2RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.OAuth2AuthorizationService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.OAuth2RegisteredClientService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import static com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props.OAuth2AuthorizationServerProperties.JwkProperties.OAUTH2_AUTHORIZATION_SERVER_JWK_PROPERTIES_VALUE;

@Configuration
public class OAuth2AuthorizationServerConfig {

    private final OAuth2AuthorizationServerConfigurer configurer = new OAuth2AuthorizationServerConfigurer();

    @Bean
    public OAuth2RegisteredClientRepository oAuth2RegisteredClientRepository(RegisteredClientDataSourceRepository registeredClientDataSourceRepository) {
        return new OAuth2RegisteredClientService(registeredClientDataSourceRepository);
    }

    @Bean
    public OAuth2AuthorizationRepository oAuth2AuthorizationRepository() {
        return new OAuth2AuthorizationService();
    }

    @Bean
    public SecurityFilterChain oauth2AuthorizationServerSecurityFilterChain(@NonNull HttpSecurity httpSecurity,
                                                                            @NonNull OAuth2AuthorizationRepository oAuth2AuthorizationRepository,
                                                                            @NonNull OAuth2RegisteredClientRepository OAuth2RegisteredClientRepository) throws Exception {
        httpSecurity.apply(configurer);
        configurer.registeredClientRepository(OAuth2RegisteredClientRepository);
        configurer.authorizationService(oAuth2AuthorizationRepository);
        configurer.init(httpSecurity);
        return httpSecurity
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll()
                .and()
                .csrf()
                .disable()
                .build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(@Value(OAUTH2_AUTHORIZATION_SERVER_JWK_PROPERTIES_VALUE) OAuth2AuthorizationServerProperties.JwkProperties jwkProperties) throws NoSuchAlgorithmException {
        var keyPairGen = KeyPairGenerator.getInstance(jwkProperties.getAlgorithm());
        keyPairGen.initialize(jwkProperties.getKeySize());
        var keys = keyPairGen.generateKeyPair();
        var publicKey = (RSAPublicKey) keys.getPublic();
        var privateKey = keys.getPrivate();
        var key = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        var jwkSet = new JWKSet(key);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource){
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtGenerator jwtGenerator(JwtEncoder jwtEncoder){
        return new JwtGenerator(jwtEncoder);
    }
}
