package com.github.olson1998.synthwave.authorizationserver.domain.port.oauth2.datasource.stereotype;

import io.hypersistence.tsid.TSID;

public interface UserIdentifiers {

    TSID getId();

    String getClientId();

    String getEmailAddress();
}
