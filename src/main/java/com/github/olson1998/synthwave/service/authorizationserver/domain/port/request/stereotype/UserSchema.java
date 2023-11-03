package com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserAffiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserProperties;

public interface UserSchema {

    UserProperties getUser();

    UserAffiliation getAffiliation();

    Password getPassword();

}
