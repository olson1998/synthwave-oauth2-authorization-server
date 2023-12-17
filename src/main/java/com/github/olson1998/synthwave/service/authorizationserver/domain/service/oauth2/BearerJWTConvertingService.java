package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.BearerJWTConverter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class BearerJWTConvertingService implements BearerJWTConverter {

    public static final String BEARER = "Bearer ";

    private final JwtDecoder jwtDecoder;

    private final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    @Override
    public Authentication convert(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .filter(bearerToken -> StringUtils.contains(bearerToken, BEARER))
                .map(bearerToken -> StringUtils.substringAfter(bearerToken, BEARER))
                .map(jwtDecoder::decode)
                .map(jwtAuthenticationConverter::convert)
                .orElse(null);
    }
}
