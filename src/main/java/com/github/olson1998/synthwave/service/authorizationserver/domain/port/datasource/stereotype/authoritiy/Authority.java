package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.authoritiy;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.common.Identifiable;
import com.github.olson1998.synthwave.support.jpa.audit.ActiveFrom;
import com.github.olson1998.synthwave.support.jpa.audit.CreatedOn;
import com.github.olson1998.synthwave.support.jpa.audit.ExpireOn;

public interface Authority extends Identifiable<Long>, CreatedOn, ExpireOn, ActiveFrom {

    String getName();

}
