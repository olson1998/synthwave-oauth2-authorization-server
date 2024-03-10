package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.ExpireOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.Identifiable;

public interface ApplicationUser extends Identifiable<Long>, CreatedOn, ExpireOn {

    String getUsername();

    String getDisplayName();
}
