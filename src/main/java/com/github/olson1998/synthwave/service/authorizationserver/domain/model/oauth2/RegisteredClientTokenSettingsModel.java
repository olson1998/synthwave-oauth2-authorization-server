package com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RegisteredClientTokenSettings;
import com.github.olson1998.synthwave.support.joda.converter.JavaDurationConverter;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.joda.time.Period;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RegisteredClientTokenSettingsModel implements RegisteredClientTokenSettings {

    private final TSID registeredClientId;

    private final Period authorizationCodeExpirePeriod;

    private final Period accessTokenExpirePeriod;

    private final OAuth2TokenFormat accessTokenFormat;

    private final Period deviceCodeExpirePeriod;

    private final Boolean reuseRefreshToken;

    private final Period refreshTokenExpirePeriod;

    private final SignatureAlgorithm idTokenSignatureAlgorithm;

    public RegisteredClientTokenSettingsModel(TSID registeredClientId, TokenSettings tokenSettings) {
        this.registeredClientId = registeredClientId;
        this.authorizationCodeExpirePeriod = new JavaDurationConverter(tokenSettings.getAuthorizationCodeTimeToLive())
                .toPeriod();
        this.accessTokenExpirePeriod = new JavaDurationConverter(tokenSettings.getAccessTokenTimeToLive())
                .toPeriod();
        this.accessTokenFormat = tokenSettings.getAccessTokenFormat();
        this.deviceCodeExpirePeriod = new JavaDurationConverter(tokenSettings.getDeviceCodeTimeToLive())
                .toPeriod();
        this.reuseRefreshToken = tokenSettings.isReuseRefreshTokens();
        this.refreshTokenExpirePeriod = new JavaDurationConverter(tokenSettings.getRefreshTokenTimeToLive())
                .toPeriod();
        this.idTokenSignatureAlgorithm = tokenSettings.getIdTokenSignatureAlgorithm();
    }

}
