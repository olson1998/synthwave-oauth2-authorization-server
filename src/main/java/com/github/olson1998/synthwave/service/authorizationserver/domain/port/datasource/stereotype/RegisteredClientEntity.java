package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.OAuth2Client;
import io.hypersistence.tsid.TSID;

public interface RegisteredClientEntity extends OAuth2Client {

    TSID getId();

}
