package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

public interface UserSchema {

    UserProperties getUser();

    UserAffiliation getAffiliation();

    Password getPassword();

}
