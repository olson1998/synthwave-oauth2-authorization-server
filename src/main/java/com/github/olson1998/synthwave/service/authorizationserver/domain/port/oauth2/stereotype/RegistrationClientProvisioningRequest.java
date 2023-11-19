package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSavingRequest;

import java.util.Optional;

public interface RegistrationClientProvisioningRequest {

    AbstractSynthWaveRegisteredClient getSynthWaveRegisteredClient();

    Optional<UserSavingRequest> getOptionalUserSavingRequest();
}
