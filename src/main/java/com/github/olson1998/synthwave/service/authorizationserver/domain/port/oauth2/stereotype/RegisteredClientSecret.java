package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import io.hypersistence.tsid.TSID;
import org.joda.time.MutableDateTime;

import java.time.Instant;

public interface RegisteredClientSecret {

    TSID getRegisteredClientId();

    String getValue();

    MutableDateTime getExpiresDateTime();
}
