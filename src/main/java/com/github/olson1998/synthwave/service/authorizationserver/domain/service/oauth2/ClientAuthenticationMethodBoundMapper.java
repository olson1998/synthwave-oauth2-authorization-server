package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.dto.ClientAuthenticationMethodBoundModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.ClientAuthenticationMethodBinding;
import io.hypersistence.tsid.TSID;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

class ClientAuthenticationMethodBoundMapper {

    Collection<ClientAuthenticationMethodBinding> map(TSID registeredClientId, Collection<ClientAuthenticationMethod> clientAuthenticationMethods){
        return Objects.requireNonNullElse(clientAuthenticationMethods, new ArrayList<ClientAuthenticationMethod>()).stream()
                .map(clientAuthenticationMethod -> new ClientAuthenticationMethodBoundModel(registeredClientId, clientAuthenticationMethod))
                .map(ClientAuthenticationMethodBinding.class::cast)
                .collect(Collectors.toSet());
    }
}
