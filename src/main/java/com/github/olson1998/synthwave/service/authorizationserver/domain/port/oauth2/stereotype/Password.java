package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import io.hypersistence.tsid.TSID;
import org.joda.time.Period;

public interface Password {

    String getValue();

    TSID getUserId();

    Period getExpirePeriod();
}
