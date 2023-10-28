package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectUrisData;
import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.embeddable.RedirectUrisValue;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectUrisDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectURI;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RedirectUrisDataSourceRepositoryProxy implements RedirectUrisDataSourceRepository {

    private final RedirectUrisJpaRepository redirectUrisJpaRepository;

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
                .toList();
        return redirectUrisJpaRepository.selectRedirectURIThatAreNotPresent(data).stream()
                .map(RedirectURI.class::cast)
                .toList();
    }

    @Override
    public void saveAll(@NonNull Collection<RedirectURI> redirectUris) {
        var data = redirectUris.stream()
                .map(RedirectUrisData::new)
                .toList();
        redirectUrisJpaRepository.saveAll(data);
    }

}
