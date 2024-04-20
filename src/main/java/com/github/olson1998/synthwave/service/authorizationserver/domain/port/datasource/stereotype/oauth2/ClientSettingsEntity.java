package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2;

import com.github.olson1998.synthwave.support.jpa.audit.CreatedOn;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

public interface ClientSettingsEntity extends CreatedOn {

    Long getRegisteredClientId();

    Boolean getRequireProofKey();

    Boolean getRequireAuthorizationConsent();

    String getJwkSetUrl();

    JwsAlgorithm getJwsAlgorithm();

    ClientSettings toSettings();

}
