package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import io.hypersistence.tsid.TSID;
import org.joda.time.MutableDateTime;

public interface UserBan {

    TSID getId();

    TSID getUserId();

    MutableDateTime getTimestamp();
}
