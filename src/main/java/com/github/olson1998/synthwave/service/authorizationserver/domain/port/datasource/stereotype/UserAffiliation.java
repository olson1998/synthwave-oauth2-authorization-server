package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import io.hypersistence.tsid.TSID;

public interface UserAffiliation {

    TSID getUserId();

    String getCompanyCode();

    String getDivision();

}
