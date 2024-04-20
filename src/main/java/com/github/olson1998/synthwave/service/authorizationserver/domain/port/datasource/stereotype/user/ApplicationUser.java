package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.Identifiable;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOn;
import com.github.olson1998.synthwave.support.jpa.audit.ExpireOn;

public interface ApplicationUser extends Identifiable<Long>, CreatedOn, ExpireOn {

    String getCompanyCode();

    String getDivision();

    String getUsername();

    String getDisplayName();
}
