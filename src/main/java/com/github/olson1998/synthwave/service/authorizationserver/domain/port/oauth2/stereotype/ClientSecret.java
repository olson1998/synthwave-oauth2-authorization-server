package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import org.joda.time.MutableDateTime;

public interface ClientSecret {

    String getValue();

    MutableDateTime getExpiresDateTime();
}
