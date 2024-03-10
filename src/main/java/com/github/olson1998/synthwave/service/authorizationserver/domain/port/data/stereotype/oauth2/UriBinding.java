package com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype.common.CreatedOn;

public interface UriBinding extends CreatedOn {

    Long getRegisteredClientId();

    Long getUriId();
}
