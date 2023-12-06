package com.github.olson1998.synthwave.service.authorizationserver.domain.service.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.AuthorizationGrantTypeBindDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.repository.AuthorizationGrantTypeRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AuthorizationGrantTypeService implements AuthorizationGrantTypeRepository {

    private final AuthorizationGrantTypeBindDataSourceRepository authorizationGrantTypeBindDataSourceRepository;

    private final AuthorizationGrantTypesBoundMapper authorizationGrantTypesBoundMapper = new AuthorizationGrantTypesBoundMapper();

    @Override
    public Set<AuthorizationGrantType> getAuthorizationGrantTypesByRegisteredClientId(TSID registeredClientId) {
        return new HashSet<>(authorizationGrantTypeBindDataSourceRepository.getAuthorizationGrantTypesByRegisteredClientId(registeredClientId));
    }

    @Override
    public void saveBindings(TSID registeredClientId, Set<AuthorizationGrantType> authorizationGrantTypeSet) {
        var bindings = authorizationGrantTypesBoundMapper.mapToAuthorizationGrantTypeBindings(
                registeredClientId,
                authorizationGrantTypeSet
        );
        authorizationGrantTypeBindDataSourceRepository.saveAll(bindings);
    }
}
