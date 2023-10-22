package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectUrisDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectUri;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RedirectUrisDataSourceRepositoryProxy implements RedirectUrisDataSourceRepository {

    private final RedirectUrisJpaRepository redirectUrisJpaRepository;

    @Override
    public Collection<RedirectUri> getAllRedirectUris() {
        return redirectUrisJpaRepository.findAll()
                .stream()
                .map(RedirectUri.class::cast)
                .toList();
    }
}
