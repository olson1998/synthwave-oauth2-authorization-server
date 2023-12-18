package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.*;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.BearerJWTConvertingService;
import com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2.OidcUserInfoJwtAuthenticationService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class OAuth2FilterChainConfig {

    public static final String LOGIN_PATH = "/login";

    public static final String LOGIN_PROCESS_ENDPOINT= LOGIN_PATH+ "/process";

    @Bean
    public JwtAuthenticationProvider synthWaveJWTAuthenticationProvider(JwtDecoder jwtDecoder){
        return new JwtAuthenticationProvider(jwtDecoder);
    }

    @Bean
    public BearerJWTConverter bearerJWTConverter(JwtDecoder jwtDecoder){
        return new BearerJWTConvertingService(jwtDecoder);
    }

    @Bean
    public OidcUserInfoJWTAuthenticationProvider jwtAuthenticationProvider(){
        return new OidcUserInfoJwtAuthenticationService();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain oauth2SecurityFilterChain(@NonNull HttpSecurity httpSecurity,
                                                         @NonNull JwtGenerator jwtGenerator,
                                                         @NonNull BearerJWTConverter bearerJWTConverter,
                                                         @NonNull OidcUserInfoJWTAuthenticationProvider synthWaveOidcUserInfoJWTAuthenticationProvider,
                                                         @NonNull OAuth2AuthorizationRepository oAuth2AuthorizationRepository,
                                                         @NonNull SynthWaveRegisteredClientRepository synthWaveRegisteredClientRepository) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .registeredClientRepository(synthWaveRegisteredClientRepository)
                .oidc(oidcConfigurer -> {
                    oidcConfigurer.clientRegistrationEndpoint(Customizer.withDefaults());
                    oidcConfigurer.userInfoEndpoint(oidcUserInfoEndpointConfigurer -> {
                        oidcUserInfoEndpointConfigurer.userInfoRequestConverter(bearerJWTConverter);
                        oidcUserInfoEndpointConfigurer.authenticationProviders(authenticationProviders -> {
                            authenticationProviders.add(new OidcUserInfoAuthenticationProvider(oAuth2AuthorizationRepository));
                            authenticationProviders.add(synthWaveOidcUserInfoJWTAuthenticationProvider);
                        });
                    });
                    oidcConfigurer.logoutEndpoint(oidcLogoutEndpointConfigurer -> {
                        oidcLogoutEndpointConfigurer.authenticationProvider(synthWaveOidcUserInfoJWTAuthenticationProvider);
                    });
                })
                .tokenGenerator(jwtGenerator);
        httpSecurity.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN_PATH));
        });
        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain loginPageSecurityFilterChain(@NonNull HttpSecurity httpSecurity,
                                                            @NonNull UserDetailsRepository userDetailsRepository) throws Exception {
        return httpSecurity
                .securityMatcher(LOGIN_PATH + "/**", LOGIN_PROCESS_ENDPOINT + "/**")
                .formLogin(Customizer.withDefaults())
                .formLogin(formLoginConfigurer -> {
                    formLoginConfigurer.failureForwardUrl(LOGIN_PATH);
                    formLoginConfigurer.loginProcessingUrl(LOGIN_PROCESS_ENDPOINT);
                })
                .userDetailsService(userDetailsRepository)
                .build();
    }

}
