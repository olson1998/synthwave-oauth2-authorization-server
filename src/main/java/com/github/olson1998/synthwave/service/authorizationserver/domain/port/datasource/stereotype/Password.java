package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import io.hypersistence.tsid.TSID;
import org.joda.time.Period;

import java.util.Optional;

public interface Password {

    TSID getId();

    TSID getUserId();

    String getValue();

    Optional<Period> getOptionalExpirePeriod();

    Boolean getLatestVersion();
}
