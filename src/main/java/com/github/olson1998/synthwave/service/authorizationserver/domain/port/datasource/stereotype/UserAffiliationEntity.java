package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAffiliation;
import io.hypersistence.tsid.TSID;

public interface UserAffiliationEntity extends UserAffiliation {

    TSID getUserId();

}
