package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.function.Supplier;

public interface RegistrationClientProvisioningSupplier extends Supplier<Optional<LinkedHashSet<RegisteredClient>>> {

}
