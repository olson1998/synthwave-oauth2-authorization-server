package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;
import io.hypersistence.tsid.TSID;

public interface UserEntity extends UserProperties {

    TSID getId();

}
