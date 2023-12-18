package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import io.hypersistence.tsid.TSID;

public interface UserEmail {

    TSID getUserId();

    String getEmailAddress();

}
