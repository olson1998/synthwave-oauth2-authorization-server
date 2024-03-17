package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.Identifiable;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

public interface AuthorizationGrantTypeEntity extends Identifiable<Long>, CreatedOn {

    AuthorizationGrantType getGrantType();
}
