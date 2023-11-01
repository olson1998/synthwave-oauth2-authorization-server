package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAccountLock;
import io.hypersistence.tsid.TSID;

public interface UserAccountLockEntity extends UserAccountLock {

    TSID getId();

}
