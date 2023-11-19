package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegisteredClientIdentifiers;
import io.hypersistence.tsid.TSID;

public interface RegisteredClientEntity extends RegisteredClientIdentifiers {

    TSID getId();

}
