package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.request.RegistrationClientProvisioningRequest;

import java.util.LinkedHashSet;
import java.util.function.Supplier;

public interface RegistrationClientRequestSupplier extends Supplier<LinkedHashSet<RegistrationClientProvisioningRequest>> {

}
