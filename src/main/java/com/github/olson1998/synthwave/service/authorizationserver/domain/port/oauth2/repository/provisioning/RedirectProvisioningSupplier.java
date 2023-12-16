package com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.provisioning;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.function.Supplier;

public interface RedirectProvisioningSupplier extends Supplier<Optional<LinkedHashSet<Redirect>>> {
}
