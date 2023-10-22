package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import org.joda.time.Period;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;

public interface AffiliationBasedTokenSettings {

    Period getAuthorizationCodeExpirePeriod();

    Period getAccessTokenExpirePeriod();

    OAuth2TokenFormat getAccessTokenFormat();

    Period getDeviceCodeExpirePeriod();

    boolean isReuseRefreshToken();

    Period getRefreshTokenExpirePeriod();

    SignatureAlgorithm getIdTokenSignatureAlgorithm();

}
