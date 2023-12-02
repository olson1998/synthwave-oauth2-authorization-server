package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import io.hypersistence.tsid.TSID;
import org.joda.time.Period;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

public interface RegisteredClientTokenSettings {

    TSID getRegisteredClientId();

    Period getAuthorizationCodeExpirePeriod();

    Period getAccessTokenExpirePeriod();

    OAuth2TokenFormat getAccessTokenFormat();

    Period getDeviceCodeExpirePeriod();

    Boolean getReuseRefreshToken();

    Period getRefreshTokenExpirePeriod();

    SignatureAlgorithm getIdTokenSignatureAlgorithm();

}
