package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientAuthenticationMethodBindingModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.model.oauth2.ClientAuthenticationMethodEntityModel;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ClientAuthenticationMethodDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.ClientAuthenticationMethodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class ClientAuthenticationMethodService implements ClientAuthenticationMethodRepository {

    private final ClientAuthenticationMethodDataSourceRepository clientAuthenticationMethodDataSourceRepository;

    @Override
    public Collection<? extends ClientAuthenticationMethodEntity> getAllMethods() {
        log.debug("Client Authentication Method: Listing all values");
        return clientAuthenticationMethodDataSourceRepository.getAllAuthenticationMethods()
                .stream()
                .map(ClientAuthenticationMethodEntityModel::new)
                .toList();
    }

    @Override
    public Collection<ClientAuthenticationMethod> getClientAuthenticationMethodSetByRegisteredClientId(Long registeredClientId) {
        return clientAuthenticationMethodDataSourceRepository.getClientAuthenticationMethodSetByRegisteredClientId(registeredClientId);
    }

    @Override
    public Collection<? extends ClientAuthenticationMethodEntity> getClientAuthenticationMethodsByExamples(Collection<? extends ClientAuthenticationMethodEntity> examples) {
        return clientAuthenticationMethodDataSourceRepository.getClientAuthenticationMethodsByExamples(examples);
    }

    @Override
    public void saveBounds(Long registeredClientId, Collection<? extends ClientAuthenticationMethodEntity> clientAuthenticationMethods) {
        var bounds = clientAuthenticationMethods.stream()
                .map(clientAuthenticationMethod -> new ClientAuthenticationMethodBindingModel(registeredClientId, clientAuthenticationMethod.getId()))
                .toList();
        clientAuthenticationMethodDataSourceRepository.saveAllBounds(bounds);
    }
}
