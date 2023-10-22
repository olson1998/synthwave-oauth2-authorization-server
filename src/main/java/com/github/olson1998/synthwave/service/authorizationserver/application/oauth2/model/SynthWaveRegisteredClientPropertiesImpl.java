package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectUri;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveClientRegisteredClientSettings;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveRegisteredClientProperties;
import io.hypersistence.tsid.TSID;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
@ToString
public class SynthWaveRegisteredClientPropertiesImpl implements SynthWaveRegisteredClientProperties {

    private final TSID registeredClientId;

    private final String clientId;

    private final String username;

    private final String passwordValue;

    private final Instant passwordExpireTime;

    private final SynthWaveClientRegisteredClientSettings registeredClientSettings;

    private final Set<String> redirectUris;

    private final Set<String> postLogoutRedirectUris;

    public SynthWaveRegisteredClientPropertiesImpl(TSID registeredClientId,
                                                   String clientId,
                                                   String username,
                                                   TSID passwordId,
                                                   String passwordValue,
                                                   Period passwordExpirePeriod,
                                                   SynthWaveClientRegisteredClientSettings registeredClientSettings) {
        this.registeredClientId = registeredClientId;
        this.clientId = clientId;
        this.username = username;
        this.passwordValue = passwordValue;
        this.passwordExpireTime = resolvePasswordExpireInstant(passwordId, passwordExpirePeriod);
        this.registeredClientSettings = registeredClientSettings;
        this.redirectUris = new HashSet<>();
        this.postLogoutRedirectUris = new HashSet<>();
    }

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

    private Instant resolvePasswordExpireInstant(TSID passwordId, Period passwordExpirePeriod){
        return Optional.ofNullable(passwordExpirePeriod)
                .map(period -> countPasswordExpireInstant(passwordId, period))
                .orElse(null);
    }

    private Instant countPasswordExpireInstant(TSID passwordId, Period passwordExpirePeriod){
        var passwordCreationInstant = passwordId.getInstant();
        var passwordCreationDateTime = new DateTime(passwordCreationInstant.toEpochMilli());
        var expireDate =passwordCreationDateTime.plus(passwordExpirePeriod);
        return Instant.ofEpochMilli(expireDate.getMillis());
    }
}
