package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.transaction.annotation.Transactional;

public interface RegisteredClientProvisioningRepository {

    @Transactional
    void provision(RegisteredClient registeredClient);

}
