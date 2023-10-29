package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.OAuth2Tokens;
import lombok.NonNull;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;

import java.util.Collection;
import java.util.Optional;

public record OAuth2TokensImpl(@NonNull Collection<OAuth2Token> collection) implements OAuth2Tokens {

    @Override
    public Optional<OAuth2RefreshToken> resolveOptionalRefreshToken() {
        return collection.stream()
                .filter(oAuth2Token -> oAuth2Token instanceof OAuth2RefreshToken)
                .map(OAuth2RefreshToken.class::cast)
                .findFirst();
    }

    @Override
    public Optional<OAuth2AccessToken> resolveOptionalAccessToken() {
        return collection.stream()
                .filter(oAuth2Token -> oAuth2Token instanceof OAuth2AccessToken)
                .map(OAuth2AccessToken.class::cast)
                .findFirst();
    }
}
