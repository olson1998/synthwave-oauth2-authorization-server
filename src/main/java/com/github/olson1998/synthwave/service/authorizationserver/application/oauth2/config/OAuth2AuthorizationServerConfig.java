package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props.OAuth2AuthorizationServerProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2AuthorizationRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OAuth2RegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.RedirectRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ScopeRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.OAuth2AuthorizationService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.OAuth2RegisteredClientService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.OAuth2TokenCustomizingService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import java.util.concurrent.Executor;

import static com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props.OAuth2AuthorizationServerProperties.EndpointsProperties.OAUTH2_AUTHORITZATION_SERVER_ENDPOINTS_PROPERTIES_VALUE;
import static com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props.OAuth2AuthorizationServerProperties.JwkProperties.OAUTH2_AUTHORIZATION_SERVER_JWK_PROPERTIES_VALUE;
import static com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.props.OAuth2AuthorizationServerProperties.LoginEndpointsProperties.OAUTH2_AUTHORIZATION_SERVER_LOGIN_ENDPOINTS_VALUE;

@Configuration
public class OAuth2AuthorizationServerConfig {

    public static final String OAUTH2_REQUEST_PATH = "/oauth2";

    public static final String LOGIN_PATH = "/login";

    public static final String ON_LOGIN_PROCESS_ENDPOINT= LOGIN_PATH + "/process";

    @Bean
    public OAuth2RegisteredClientRepository oAuth2RegisteredClientRepository(Executor executor,
                                                                             PasswordEncoder passwordEncoder,
                                                                             RedirectRepository redirectRepository,
                                                                             ScopeRepository scopeRepository,
                                                                             RegisteredClientDataSourceRepository registeredClientDataSourceRepository,
                                                                             RegisteredClientSecretDataSourceRepository registeredClientSecretDataSourceRepository,
                                                                             ClientSettingsDataSourceRepository clientSettingsDataSourceRepository,
                                                                             TokenSettingsDataSourceRepository tokenSettingsDataSourceRepository,
                                                                             AuthorizationGrantTypeDatasourceRepository authorizationGrantTypeDatasourceRepository,
                                                                             ClientAuthenticationMethodDataSourceRepository clientAuthenticationMethodDataSourceRepository) {
        return new OAuth2RegisteredClientService(
                executor,
                passwordEncoder,
                redirectRepository,
                scopeRepository,
                registeredClientDataSourceRepository,
                registeredClientSecretDataSourceRepository,
                clientSettingsDataSourceRepository,
                tokenSettingsDataSourceRepository,
                authorizationGrantTypeDatasourceRepository,
                clientAuthenticationMethodDataSourceRepository
        );
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(@Value(OAUTH2_AUTHORITZATION_SERVER_ENDPOINTS_PROPERTIES_VALUE) OAuth2AuthorizationServerProperties.EndpointsProperties endpointsProperties) {
        var deviceEndpoints = endpointsProperties.getDevice();
        var oidcEndpoints = endpointsProperties.getOidc();
        var tokenEndpoints = endpointsProperties.getToken();
        return AuthorizationServerSettings.builder()
                .authorizationEndpoint(OAUTH2_REQUEST_PATH + endpointsProperties.getAuthorization())
                .jwkSetEndpoint(OAUTH2_REQUEST_PATH + endpointsProperties.getJwk())
                .tokenIntrospectionEndpoint(OAUTH2_REQUEST_PATH + tokenEndpoints.getIntrospection())
                .tokenRevocationEndpoint(OAUTH2_REQUEST_PATH + tokenEndpoints.getRevocation())
                .tokenEndpoint(OAUTH2_REQUEST_PATH + tokenEndpoints.getEndpoint())
                .deviceAuthorizationEndpoint(OAUTH2_REQUEST_PATH + deviceEndpoints.getAuthorization())
                .deviceVerificationEndpoint(OAUTH2_REQUEST_PATH + deviceEndpoints.getVerification())
                .oidcClientRegistrationEndpoint(OAUTH2_REQUEST_PATH + oidcEndpoints.getClientRegistration())
                .oidcUserInfoEndpoint(OAUTH2_REQUEST_PATH + oidcEndpoints.getUserInfo())
                .oidcLogoutEndpoint(OAUTH2_REQUEST_PATH + oidcEndpoints.getLogout())
                .build();
    }

    @Bean
    public OAuth2AuthorizationRepository oAuth2AuthorizationRepository() {
        return new OAuth2AuthorizationService();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain oauth2AuthorizationServerSecurityFilterChain(@NonNull HttpSecurity httpSecurity,
                                                                            @NonNull JwtGenerator jwtGenerator,
                                                                            @NonNull OAuth2AuthorizationRepository oAuth2AuthorizationRepository,
                                                                            @NonNull OAuth2RegisteredClientRepository oAuth2RegisteredClientRepository) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults())
                .registeredClientRepository(oAuth2RegisteredClientRepository)
                .tokenGenerator(jwtGenerator);
        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain applicationSecurityFilterChain(HttpSecurity httpSecurity,
                                                              @Value(OAUTH2_AUTHORIZATION_SERVER_LOGIN_ENDPOINTS_VALUE) OAuth2AuthorizationServerProperties.LoginEndpointsProperties loginEndpointsProperties) throws Exception {
        return httpSecurity
                .securityMatcher(
                        OAUTH2_REQUEST_PATH + "/**",
                        "/login"
                ).formLogin(Customizer.withDefaults())
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
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtEncodingContextOAuth2TokenCustomizer() {
        return new OAuth2TokenCustomizingService();
    }

    @Bean
    public JwtGenerator jwtGenerator(JwtEncoder jwtEncoder,
                                     OAuth2TokenCustomizer<JwtEncodingContext> jwtEncodingContextOAuth2TokenCustomizer){
        var jwtGenerator = new JwtGenerator(jwtEncoder);
        jwtGenerator.setJwtCustomizer(jwtEncodingContextOAuth2TokenCustomizer);
        return jwtGenerator;
    }
}
