package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectURIData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectURIDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.RedirectURI;
import io.hypersistence.tsid.TSID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class RedirectURIJpaRepositoryProxy implements RedirectURIDataSourceRepository {

    private final RedirectURIJpaRepository redirectUrisJpaRepository;
    @Override
    public Collection<RedirectURI> getRedirectURIByRegisteredClientIdCompanyCodeAndDivision(TSID registeredClientId, String companyCode, String division) {
        return redirectUrisJpaRepository.selectRedirectURIByClientIdCompanyCodeAndDivision(registeredClientId, companyCode, division).stream()
                .map(RedirectURI.class::cast)
                .toList();
    }

    @Override
    public void saveAll(@NonNull Collection<RedirectURI> redirectUris) {
        var data = redirectUris.stream()
                .map(RedirectURIData::new)
                .toList();
        redirectUrisJpaRepository.saveAll(data);
    }

}
