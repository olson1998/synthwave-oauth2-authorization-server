package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientSettings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Set;

import static org.springframework.security.oauth2.core.AuthorizationGrantType.*;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.*;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RegisteredClientType implements RegisteredClientSettings {

    HUMAN(true, false, Set.of(AUTHORIZATION_CODE, JWT_BEARER, REFRESH_TOKEN), Set.of(CLIENT_SECRET_POST, CLIENT_SECRET_BASIC, NONE)
    ),
    DEVICE(true, false,Set.of(DEVICE_CODE, JWT_BEARER, REFRESH_TOKEN), Set.of(CLIENT_SECRET_POST, CLIENT_SECRET_BASIC, NONE));

    private final boolean requireProofKey;

    private final boolean requireAuthorizationConsent;

    private final Set<AuthorizationGrantType> authorizationGrantTypes;

    private final Set<ClientAuthenticationMethod> clientAuthenticationMethods;

    @Override
    public boolean isHuman() {
        return this == HUMAN;
    }

    @Override
    public boolean isDevice() {
        return this == DEVICE;
    }

    @Override
    public ClientSettings.Builder fabricateClientSettingsBuilder() {
        return ClientSettings.builder()
                .requireProofKey(requireProofKey)
                .requireAuthorizationConsent(requireAuthorizationConsent);
    }
}
