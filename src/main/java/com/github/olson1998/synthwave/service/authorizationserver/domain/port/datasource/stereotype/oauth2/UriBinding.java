package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2;

import com.github.olson1998.synthwave.support.jpa.audit.CreatedOn;

public interface UriBinding extends CreatedOn {

    Long getRegisteredClientId();

    Long getUriId();
}
