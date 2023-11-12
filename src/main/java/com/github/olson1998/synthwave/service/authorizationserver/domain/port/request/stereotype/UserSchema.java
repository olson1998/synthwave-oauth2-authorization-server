package com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Affiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;

public interface UserSchema {

    UserProperties getUser();

    Affiliation getAffiliation();

    Password getPassword();

}
