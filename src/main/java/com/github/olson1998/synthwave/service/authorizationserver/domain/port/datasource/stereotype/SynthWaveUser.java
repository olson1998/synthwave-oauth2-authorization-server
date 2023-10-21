package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import io.hypersistence.tsid.TSID;
import org.joda.time.Period;

public interface SynthWaveUser {

    TSID getId();

    String getUsername();

    boolean isEnabled();

    Period getExpirePeriod();
}
