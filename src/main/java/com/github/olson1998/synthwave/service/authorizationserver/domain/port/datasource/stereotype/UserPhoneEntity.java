package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserPhone;

public interface UserPhoneEntity extends UserPhone {

    Boolean getVerified();
}
