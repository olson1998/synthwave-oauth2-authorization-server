package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.CreatedOn;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;

public interface ClientSettings extends CreatedOn {

    Long getRegisteredClientId();

    Boolean getRequireProofKey();

    Boolean getRequireAuthorizationConsent();

    String getJwkSetUrl();

    JwsAlgorithm getJwsAlgorithm();

}
