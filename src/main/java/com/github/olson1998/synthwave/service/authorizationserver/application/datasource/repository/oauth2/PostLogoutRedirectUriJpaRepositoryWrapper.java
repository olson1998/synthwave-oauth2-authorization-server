package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository.oauth2;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.oauth2.PostLogoutRedirectUriDataSourceRepository;
import com.github.olson1998.synthwave.support.web.util.URIModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostLogoutRedirectUriJpaRepositoryWrapper implements PostLogoutRedirectUriDataSourceRepository {

    private final PostLogoutRedirectUriJpaRepository postLogoutRedirectUriJpaRepository;

    @Override
    public Set<URIModel> getPostLogoutRedirectUriByRegisteredClientId(Long registeredClientId) {
        return postLogoutRedirectUriJpaRepository.selectPostLogoutRedirectUriModelByRegisteredClientId(registeredClientId);
    }
}
