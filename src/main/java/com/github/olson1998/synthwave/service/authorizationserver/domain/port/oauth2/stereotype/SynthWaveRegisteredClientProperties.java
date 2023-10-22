package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectUri;
import io.hypersistence.tsid.TSID;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

public interface SynthWaveRegisteredClientProperties {

    TSID getRegisteredClientId();

    String getClientId();

    String getUsername();

    String getPasswordValue();

    Instant getPasswordExpireTime();

    SynthWaveClientRegisteredClientSettings getRegisteredClientSettings();

    Set<String> getRedirectUris();

    Set<String> getPostLogoutRedirectUris();

    void appendUnresolvedUris(Collection<RedirectUri> redirectUris);

}
