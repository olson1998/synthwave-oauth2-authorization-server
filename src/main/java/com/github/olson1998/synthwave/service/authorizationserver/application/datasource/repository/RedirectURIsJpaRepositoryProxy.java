package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectURIsData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.RedirectUrisValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectURIsDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RedirectURIsJpaRepositoryProxy implements RedirectURIsDataSourceRepository {

    private final RedirectURIsJpaRepository redirectUrisJpaRepository;

    @Override
    public Collection<RedirectURI> getAllRedirectUris() {
        return redirectUrisJpaRepository.findAll()
                .stream()
                .map(RedirectURI.class::cast)
                .toList();
    }

    @Override
    public Collection<RedirectURI> getAllNotPresentRedirectUris(@NonNull Collection<RedirectURI> redirectUris) {
        var data = redirectUris.stream()
                .map(RedirectUrisValue::new)
                .collect(Collectors.toSet());
        return redirectUrisJpaRepository.selectRedirectURIThatAreNotPresent(data).stream()
                .map(RedirectURI.class::cast)
                .toList();
    }

    @Override
    public void saveAll(@NonNull Collection<RedirectURI> redirectUris) {
        var data = redirectUris.stream()
                .map(RedirectURIsData::new)
                .collect(Collectors.toSet());
        redirectUrisJpaRepository.saveAll(data);
    }

    @Override
    public int deleteAll(@NonNull Collection<RedirectURI> redirectURIs) {
        var data = redirectURIs.stream()
                .map(RedirectUrisValue::new)
                .collect(Collectors.toSet());
        return redirectUrisJpaRepository.deleteRedirectURIByValue(data);
    }

}
