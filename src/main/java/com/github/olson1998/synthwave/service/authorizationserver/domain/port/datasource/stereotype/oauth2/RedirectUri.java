package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.Identifiable;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOn;
import com.github.olson1998.synthwave.support.jpa.audit.ExpireOn;

public interface RedirectUri extends Identifiable<Long>, CreatedOn, ExpireOn {

    String getValue();
}
