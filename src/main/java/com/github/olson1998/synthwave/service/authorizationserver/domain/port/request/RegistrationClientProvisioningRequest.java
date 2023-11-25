package com.github.olson1998.synthwave.service.authorizationserver.domain.port.request;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSavingRequest;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Optional;

public interface RegistrationClientProvisioningRequest {

    RegisteredClient getRegisteredClient();

    Optional<UserSavingRequest> getOptionalUserSavingRequest();
}
