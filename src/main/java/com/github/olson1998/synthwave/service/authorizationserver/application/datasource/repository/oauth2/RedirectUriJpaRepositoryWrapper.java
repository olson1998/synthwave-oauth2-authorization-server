package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.RedirectUriDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import lombok.RequiredArgsConstructor;
import org.joda.time.MutableDateTime;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedirectUriJpaRepositoryWrapper implements RedirectUriDataSourceRepository {

    private final RedirectUriJpaRepository redirectUriJpaRepository;

    @Override
    public Collection<RedirectUri> getRedirectUriByExamples(Collection<? extends RedirectUri> redirectUriExamples) {
        return null;
    }

    @Override
    public Collection<RedirectUri> getPostLogoutRedirectUriByExamples(Collection<? extends RedirectUri> redirectUriExamples) {
        return null;
    }

    @Override
    public Set<String> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId) {
        return null;
    }

    @Override
    public Set<String> getRedirectUriByRegisteredClientIdWithTimestamp(Long registeredClientId, MutableDateTime timestamp) {
        return redirectUriJpaRepository.selectRedirectUriByRegisteredClientId(registeredClientId, timestamp);
    }
}
