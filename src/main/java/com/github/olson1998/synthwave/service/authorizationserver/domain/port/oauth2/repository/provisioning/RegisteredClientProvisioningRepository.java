package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public interface RegisteredClientProvisioningRepository {

    void provision(RegisteredClient registeredClient);

}
