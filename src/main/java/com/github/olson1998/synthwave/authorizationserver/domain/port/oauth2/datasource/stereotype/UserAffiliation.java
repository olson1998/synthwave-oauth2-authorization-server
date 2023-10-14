package com.github.olson1998.synthwave.authorizationserver.domain.port.oauth2.datasource.stereotype;

import io.hypersistence.tsid.TSID;

public interface UserAffiliation {

    TSID getUserId();

    String getCompanyCode();

    String getDivision();

}
