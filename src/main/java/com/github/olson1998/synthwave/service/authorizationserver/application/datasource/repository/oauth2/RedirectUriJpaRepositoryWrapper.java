package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RedirectUriDataSourceRepository;
import com.github.olson1998.synthwave.support.web.util.URIModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedirectUriJpaRepositoryWrapper implements RedirectUriDataSourceRepository {

    private final RedirectUriJpaRepository redirectUriJpaRepository;

    @Override
    public Set<URIModel> getRedirectUriByRegisteredClientId(Long registeredClientId) {
        return redirectUriJpaRepository.selectRedirectUriModelByRegisteredClientId(registeredClientId);
    }
}
