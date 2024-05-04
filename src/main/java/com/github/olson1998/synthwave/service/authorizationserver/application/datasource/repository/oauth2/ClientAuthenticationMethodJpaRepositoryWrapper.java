package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ClientAuthenticationMethodBindingData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.oauth2.ClientAuthenticationMethodData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.ClientAuthenticationMethodDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodBinding;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.ClientAuthenticationMethodEntity;
import com.github.olson1998.synthwave.support.jpa.spec.JpaSpecificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientAuthenticationMethodJpaRepositoryWrapper implements ClientAuthenticationMethodDataSourceRepository {

    private final ClientAuthenticationMethodJpaRepository clientAuthenticationMethodJpaRepository;

    private final ClientAuthenticationMethodBindingJpaRepository clientAuthenticationMethodBindingJpaRepository;

    @Override
    public Collection<? extends ClientAuthenticationMethodEntity> getAllAuthenticationMethods() {
        return clientAuthenticationMethodJpaRepository.findAll(Sort.by("id"));
    }

    @Override
    public Collection<? extends ClientAuthenticationMethodEntity> getClientAuthenticationMethodsByExamples(Collection<? extends ClientAuthenticationMethodEntity> clientAuthenticationMethodExamples) {
        var dataExamplesSpec = createDataExamplesSpec(clientAuthenticationMethodExamples);
        return clientAuthenticationMethodJpaRepository.findAll(dataExamplesSpec);
    }

    @Override
    public Collection<? extends ClientAuthenticationMethodEntity> getClientAuthenticationMethodsByMethod(Collection<ClientAuthenticationMethod> clientAuthenticationMethods) {
        return clientAuthenticationMethodJpaRepository.selectClientAuthenticationMethodByMethods(clientAuthenticationMethods);
    }

    @Override
    public Set<ClientAuthenticationMethod> getClientAuthenticationMethodSetByRegisteredClientId(Long registeredClientId) {
        return clientAuthenticationMethodJpaRepository.selectClientAuthenticationMethodsByRegisteredClientId(registeredClientId);
    }

    @Override
    public Collection<? extends ClientAuthenticationMethodEntity> saveAll(Collection<? extends ClientAuthenticationMethodEntity> clientAuthenticationMethodCollection) {
        var data = clientAuthenticationMethodCollection.stream()
                .map(ClientAuthenticationMethodData::new)
                .toList();
        return clientAuthenticationMethodJpaRepository.saveAll(data);
    }

    @Override
    public void saveAllBounds(Collection<? extends ClientAuthenticationMethodBinding> bounds) {
        var data = bounds.stream()
                .map(ClientAuthenticationMethodBindingData::new)
                .toList();
        clientAuthenticationMethodBindingJpaRepository.saveAll(data);
    }

    private Specification<ClientAuthenticationMethodData> createDataExamplesSpec(Collection<? extends ClientAuthenticationMethodEntity> clientAuthenticationMethods) {
        return clientAuthenticationMethods.stream()
                .map(ClientAuthenticationMethodData::new)
                .collect(Collectors.collectingAndThen(Collectors.toList(), JpaSpecificationUtil::createDataExamplesSpecChain));
    }

}
