package com.github.olson1998.synthwave.service.authorizationserver.domain.model.request;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.AbstractSynthWaveRegisteredClient;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegistrationClientProvisioningRequest;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSavingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RegistrationClientProvisioningRequestImpl implements RegistrationClientProvisioningRequest {

    private final AbstractSynthWaveRegisteredClient synthWaveRegisteredClient;

    private final UserSavingRequest userSavingRequest;

    @Override
    public Optional<UserSavingRequest> getOptionalUserSavingRequest() {
        return Optional.ofNullable(userSavingRequest);
    }
}
