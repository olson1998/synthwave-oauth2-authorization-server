package com.github.olson1998.synthwave.service.authorizationserver.domain.model.request;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RegistrationClientProvisioningRequest;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.stereotype.UserSavingRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class RegistrationClientProvisioningRequestImpl implements RegistrationClientProvisioningRequest {

    public static final String REGISTRATION_CLIENT_REQUEST_REGISTERED_CLIENT_JSON_FIELD = "registered_client";

    public static final String REGISTRATION_CLIENT_REQUEST_USER_SAVING_REQUEST_JSON_FIELD = "user_saving_request";

    private final RegisteredClient registeredClient;

    private final UserSavingRequest userSavingRequest;

    @Override
    public Optional<UserSavingRequest> getOptionalUserSavingRequest() {
        return Optional.ofNullable(userSavingRequest);
    }
}
