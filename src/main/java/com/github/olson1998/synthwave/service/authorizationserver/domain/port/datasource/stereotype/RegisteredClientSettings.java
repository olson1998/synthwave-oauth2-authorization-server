package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import io.hypersistence.tsid.TSID;

public interface RegisteredClientSettings {

    TSID getRegisteredClientId();

    boolean isRequireProofKey();

    boolean isRequireAuthorizationConsent();
}
