package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

public interface SynthWaveRegisteredClientProperties {

    TSID getRegisteredClientId();

    String getCompanyCode();

    String getDivision();

    String getClientId();

    String getUsername();

    String getPasswordValue();

    Instant getPasswordExpireTime();

    SynthWaveClientRegisteredClientSettings getRegisteredClientSettings();

    TokenSettings getTokenSettings();

    Set<String> getRedirectUris();

    Set<String> getPostLogoutRedirectUris();

    void appendUnresolvedUris(Collection<RedirectURI> redirectUris);

}
