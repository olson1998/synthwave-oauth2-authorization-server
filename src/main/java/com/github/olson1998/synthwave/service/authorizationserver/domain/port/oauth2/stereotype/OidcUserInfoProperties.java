package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import io.hypersistence.tsid.TSID;

import java.time.ZoneId;

public interface OidcUserInfoProperties {

    TSID getUserId();

    String getUsername();

    String getEmailAddress();

    Boolean getEmailAddressVerified();

    String getPhoneNumber();

    Boolean getPhoneNumberVerified();

    String getName();

    String getMiddleName();

    String getGivenName();

    UserGenderId getUserGenderId();

    ZoneId getUserZoneId();
}
