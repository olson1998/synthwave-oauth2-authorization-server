package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.ActiveFrom;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.ExpireOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.Identifiable;

public interface RegisteredClientProperties extends Identifiable<Long>, CreatedOn, ExpireOn, ActiveFrom {

    String getClientId();

    String getName();

}
