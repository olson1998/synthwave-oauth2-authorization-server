package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.config;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.SynthWaveRegisteredClientRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.UserDetailsRepository;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.JwtClientAssertionAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtBearerTokenAuthenticationConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class OAuth2FilterChainConfig {

    public static final String LOGIN_PATH = "/login";

    public static final String LOGIN_PROCESS_ENDPOINT= LOGIN_PATH+ "/process";

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(JwtDecoder jwtDecoder){
        return new JwtAuthenticationProvider(jwtDecoder);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain oauth2SecurityFilterChain(@NonNull HttpSecurity httpSecurity,
                                                         @NonNull JwtGenerator jwtGenerator,
                                                         @NonNull JwtAuthenticationProvider jwtAuthenticationProvider,
                                                         @NonNull SynthWaveRegisteredClientRepository synthWaveRegisteredClientRepository) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .registeredClientRepository(synthWaveRegisteredClientRepository)
                .oidc(oidcConfigurer -> {
                    oidcConfigurer.clientRegistrationEndpoint(Customizer.withDefaults());
                    oidcConfigurer.userInfoEndpoint(oidcUserInfoEndpointConfigurer -> {
                        oidcUserInfoEndpointConfigurer.authenticationProviders(authenticationProviders -> {
                            authenticationProviders.add(jwtAuthenticationProvider);
                        });
                    });
                    oidcConfigurer.logoutEndpoint(oidcLogoutEndpointConfigurer -> {
                        oidcLogoutEndpointConfigurer.authenticationProvider(jwtAuthenticationProvider);
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
