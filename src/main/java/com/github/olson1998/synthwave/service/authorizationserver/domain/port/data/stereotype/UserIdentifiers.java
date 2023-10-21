package com.github.olson1998.synthwave.service.authorizationserver.domain.port.data.stereotype;

import io.hypersistence.tsid.TSID;
import org.joda.time.Period;

public interface UserIdentifiers {

    TSID getId();

    String getClientId();

    String getUsername();

    String getEmailAddress();

    boolean isEnabled();

    Period getExpirePeriod();
}
