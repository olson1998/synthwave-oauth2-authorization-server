package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype;

import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

public interface ClientAuthenticationMethodBinding {

    TSID getRegisteredClientId();

    ClientAuthenticationMethod getClientAuthenticationMethod();
}
