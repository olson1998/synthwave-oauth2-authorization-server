package com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.Identifiable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

public interface AuthorizationGrantTypeWrapper extends Identifiable<Long>, CreatedOn {

    AuthorizationGrantType getGrantType();
}
