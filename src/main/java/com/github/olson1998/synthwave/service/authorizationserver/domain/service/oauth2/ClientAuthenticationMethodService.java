package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.ClientAuthenticationMethodBindDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ClientAuthenticationMethodRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Set;

@RequiredArgsConstructor
public class ClientAuthenticationMethodService implements ClientAuthenticationMethodRepository {

    private final ClientAuthenticationMethodBindDataSourceRepository clientAuthenticationMethodBindDataSourceRepository;

    private final ClientAuthenticationMethodBoundMapper clientAuthenticationMethodBoundMapper = new ClientAuthenticationMethodBoundMapper();

    @Override
    public Set<ClientAuthenticationMethod> getClientAuthenticationMethodByRegisteredClientId(TSID registeredClientId) {
        return clientAuthenticationMethodBindDataSourceRepository.getClientAuthenticationMethodsByRegisteredClientId(registeredClientId);
    }

    @Override
    public void saveBindings(TSID registeredClientId, Set<ClientAuthenticationMethod> clientAuthenticationMethodSet) {
        var bindings = clientAuthenticationMethodBoundMapper.map(
                registeredClientId,
                clientAuthenticationMethodSet
        );
        clientAuthenticationMethodBindDataSourceRepository.saveAll(bindings);
    }
}
