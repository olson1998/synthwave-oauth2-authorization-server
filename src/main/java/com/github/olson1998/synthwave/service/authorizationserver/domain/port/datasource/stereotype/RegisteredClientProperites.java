package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import io.hypersistence.tsid.TSID;

public interface RegisteredClientProperites {

    TSID getId();

    TSID getUserId();

    String getClientId();
}
