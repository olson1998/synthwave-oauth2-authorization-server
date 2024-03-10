package com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.user;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.CreatedOn;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.ExpireOn;

public interface ApplicationUser extends CreatedOn, ExpireOn {

    Long getId();

    String getUsername();

    String getDisplayName();
}
