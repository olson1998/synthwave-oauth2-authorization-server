package com.github.olson1998.synthwave.service.authorizationserver.application.oauth2.model;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant.UserGender;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.OidcUserInfoProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserGenderId;
import io.hypersistence.tsid.TSID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.joda.time.MutableDateTime;

import java.time.ZoneId;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class OidcUserInfoPropertiesData implements OidcUserInfoProperties {

    private final TSID userId;

    private final String username;

    private final MutableDateTime userExpireDate;

    private final String emailAddress;

    private final Boolean emailAddressVerified;

    private final String phoneNumber;

    private final Boolean phoneNumberVerified;

    private final String name;

    private final String middleName;

    private final String givenName;

    private final UserGenderId userGenderId;

    private final ZoneId userZoneId;

}
