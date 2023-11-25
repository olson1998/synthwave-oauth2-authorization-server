package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedirectJpaRepositoryProxy implements RedirectDataSourceRepository {

    private final RedirectJpaRepository redirectUrisJpaRepository;

    @Override
    public Collection<RedirectEntity> getRedirectURIByRegisteredClientId(@NonNull TSID registeredClientId) {
        return redirectUrisJpaRepository.selectRedirectURIByRegisteredClientId(registeredClientId).stream()
                .map(RedirectEntity.class::cast)
                .toList();
    }

    @Override
    public Collection<RedirectEntity> getRedirectURICollectionByRedirectURISetAndPostLogoutRedirectURISet(Set<String> redirectURISet, Set<String> postLogoutRedirectURISet) {
        return redirectUrisJpaRepository.selectRedirectURIIdInRedirectURISetAndPostLogoutRedirectURISet(redirectURISet, postLogoutRedirectURISet).stream()
                .map(RedirectEntity.class::cast)
                .toList();
    }

    @Override
    public Collection<RedirectEntity> saveAll(@NonNull Collection<Redirect> redirectUrises) {
        var data = redirectUrises.stream()
                .map(RedirectData::new)
                .toList();
        return redirectUrisJpaRepository.saveAll(data).stream()
                .map(RedirectEntity.class::cast)
                .toList();
    }

}
