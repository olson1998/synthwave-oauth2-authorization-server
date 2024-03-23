package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.role;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.ExpireOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.Identifiable;

public interface Role extends Identifiable<Long>, CreatedOn, ExpireOn {

    String getName();
}
