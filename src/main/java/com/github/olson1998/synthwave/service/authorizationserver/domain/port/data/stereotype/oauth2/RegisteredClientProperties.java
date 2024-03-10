package com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.ActiveFrom;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.ExpireOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.Identifiable;

public interface RegisteredClientProperties extends Identifiable<Long>, CreatedOn, ExpireOn, ActiveFrom {

    String getClientId();

    String getName();

}
