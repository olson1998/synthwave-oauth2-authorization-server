package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.PostLogoutRedirectUriDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.oauth2.RedirectUri;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostLogoutRedirectUriJpaRepositoryWrapper implements PostLogoutRedirectUriDataSourceRepository {

    private final PostLogoutRedirectUriJpaRepository postLogoutRedirectUriJpaRepository;

    @Override
    public Collection<RedirectUri> getRedirectUriByExamples(Collection<? extends RedirectUri> redirectUriExamples) {
        return null;
    }

    @Override
    public Set<String> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId) {
        return postLogoutRedirectUriJpaRepository.selectPostLogoutRedirectUriByRegisteredClientId(registeredClientId);
    }
}
