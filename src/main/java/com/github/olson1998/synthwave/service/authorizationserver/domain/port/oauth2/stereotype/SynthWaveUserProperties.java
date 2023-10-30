package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.Password;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserAffiliation;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.UserProperties;

public interface SynthWaveUserProperties {

    UserProperties getUserProperties();

    UserAffiliation getAffiliation();

    Password getPassword();

}
