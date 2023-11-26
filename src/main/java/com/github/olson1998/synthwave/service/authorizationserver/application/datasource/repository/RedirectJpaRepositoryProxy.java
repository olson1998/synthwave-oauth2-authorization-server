package com.github.olson1998.synthwave.service.authorizationserver.application.datasource.repository;

import com.github.olson1998.synthwave.service.authorizationserver.application.datasource.entity.RedirectData;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.repository.RedirectDataSourceRepository;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.datasource.stereotype.RedirectEntity;
import com.github.olson1998.synthwave.service.authorizationserver.domain.port.oauth2.stereotype.Redirect;
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
    public Collection<RedirectEntity> getRedirectByAffiliation(@NonNull String companyCode,@NonNull String division) {
        return redirectJpaRepository.selectRedirectByCompanyCodeAndDivision(companyCode, division).stream()
                .map(RedirectEntity.class::cast)
                .toList();
    }

    @Override
    public Collection<RedirectEntity> getRedirectByRedirectAndPostLogoutURISet(Set<String> redirects, Set<String> postLogoutRedirects) {
        return redirectJpaRepository.selectRedirectByRedirectAndPostLogoutURICompanyCodeAndDivision(redirects, postLogoutRedirects).stream()
                .map(RedirectEntity.class::cast)
                .toList();
    }

    @Override
    public Collection<RedirectEntity> getRedirectByRedirectAndPostLogoutURISetAndAffiliation(@NonNull Set<String> redirects,
                                                                                             @NonNull Set<String> postLogoutRedirects,
                                                                                             @NonNull String companyCode,
                                                                                             @NonNull String division) {
        return redirectJpaRepository.selectRedirectByRedirectAndPostLogoutURICompanyCodeAndDivision(
                redirects,
                postLogoutRedirects,
                companyCode,
                division
        ).stream()
                .map(RedirectEntity.class::cast)
                .toList();
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
