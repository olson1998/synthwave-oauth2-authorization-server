package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.OidcUserInfoJWTAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

public class JWTAuthenticationProvidingService implements OidcUserInfoJWTAuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new OidcUserInfoAuthenticationToken(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Optional.ofNullable(authentication)
                .map(aClass -> aClass.equals(JwtAuthenticationToken.class))
                .orElse(false);
    }
}
