package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.SynthWaveUserProperties;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.UserMetadata;

public interface UserProvisioningRepository {

    boolean isProvisioningRequired(String username);

    UserMetadata provision(SynthWaveUserProperties synthWaveUserProperties);
}
