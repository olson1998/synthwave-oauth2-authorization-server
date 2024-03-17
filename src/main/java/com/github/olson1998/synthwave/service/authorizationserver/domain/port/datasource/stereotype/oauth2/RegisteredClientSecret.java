package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.ExpireOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.Identifiable;

public interface RegisteredClientSecret extends Identifiable<Long>, CreatedOn, ExpireOn {

    Long getRegisteredClientId();

    String getValue();

}
