package com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.ClientAuthenticationMethodBinding;
import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Collection;
import java.util.Set;

public interface ClientAuthenticationMethodBindDataSourceRepository {

    Set<ClientAuthenticationMethod> getClientAuthenticationMethodsByRegisteredClientId(TSID registeredClientId);

    void save(ClientAuthenticationMethodBinding clientAuthenticationMethodBinding);

    void saveAll(Collection<ClientAuthenticationMethodBinding> clientAuthenticationMethodBindings);
}
