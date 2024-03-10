package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.ActiveFrom;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.ExpireOn;

public interface ParentAuthority extends CreatedOn, ExpireOn, ActiveFrom {

    Long getAuthorityId();

    Long getUpperAuthorityId();

}
