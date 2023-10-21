package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveRegisteredClientProperties;
import io.hypersistence.tsid.TSID;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@RequiredArgsConstructor
public class SynthWaveRegisteredClientPropertiesImpl implements SynthWaveRegisteredClientProperties {

    private final TSID registeredClientId;

    private final String clientId;

    private final String username;

    private final String passwordValue;

    private final Instant passwordExpireTime;

    private final Set<String> redirectUris = new HashSet<>();

    private final Set<String> postLogoutRedirectUris = new HashSet<>();

    @Override
    public void appendUnresolvedUris(@NonNull Collection<RedirectUri> redirectUrisCollection) {
        redirectUrisCollection.forEach(redirectUri -> {
            var uri = redirectUri.getRedirectUri();
            if(redirectUri.isPostLogin()){
                redirectUris.add(uri);
            } else if (redirectUri.isPostLogout()) {
                postLogoutRedirectUris.add(uri);
            }
        });
    }
}
