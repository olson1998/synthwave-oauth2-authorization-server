package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import io.hypersistence.tsid.TSID;

import java.time.ZoneId;

public interface UserInfo {

    TSID getUserId();

    String getName();

    String getMiddleName();

    String getGivenName();

    ZoneId getUserZoneId();
}
