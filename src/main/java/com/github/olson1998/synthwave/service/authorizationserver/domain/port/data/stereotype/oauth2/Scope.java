package com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.Identifiable;

public interface Scope extends Identifiable<Long>, CreatedOn {

    String getName();
}
