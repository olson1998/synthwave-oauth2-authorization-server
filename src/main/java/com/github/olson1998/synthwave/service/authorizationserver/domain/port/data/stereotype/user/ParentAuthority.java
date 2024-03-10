package com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.ActiveFrom;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.ExpireOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.Identifiable;

public interface ParentAuthority extends CreatedOn, ExpireOn, ActiveFrom {

    Long getAuthorityId();

    Long getUpperAuthorityId();

}
