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

    private final RedirectJpaRepository redirectJpaRepository;

    @Override
    public Collection<RedirectEntity> getRedirectByRegisteredClientId(TSID registeredClientId) {
        return redirectJpaRepository.selectRedirectByRegisteredClientId(registeredClientId).stream()
                .map(RedirectEntity.class::cast)
                .toList();
    }

    @Override
    public Collection<RedirectEntity> getRedirectFromURISet(Set<String> redirectURISet, Set<String> postLogoutRedirectURISet) {
        return redirectJpaRepository.selectRedirectByRedirectAndPostLogoutURI(redirectURISet, postLogoutRedirectURISet).stream()
                .map(RedirectEntity.class::cast)
                .toList();
    }

    @Override
    public RedirectEntity save(Redirect redirect) {
        return redirectJpaRepository.save(new RedirectData(redirect));
    }

    @Override
    public Collection<RedirectEntity> saveAll(@NonNull Collection<Redirect> redirectCollection) {
        var data = redirectCollection.stream()
                .map(RedirectData::new)
                .toList();
        return redirectJpaRepository.saveAll(data).stream()
                .map(RedirectEntity.class::cast)
                .toList();
    }

}
