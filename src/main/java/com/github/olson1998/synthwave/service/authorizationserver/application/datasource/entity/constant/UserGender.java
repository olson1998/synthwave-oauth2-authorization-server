package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.constant;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserGenderId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserGender implements UserGenderId {

    MALE("male", 'M'),
    FEMALE("female", 'F'),
    NON_BINARY("non binary", 'B'),
    NOT_SPECIFIED("not specified", 'U');

    private final String fullName;

    private final char genderSymbol;

}
